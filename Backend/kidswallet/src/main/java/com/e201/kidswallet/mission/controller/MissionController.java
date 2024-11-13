package com.e201.kidswallet.mission.controller;

import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.fcm.service.FcmService;
import com.e201.kidswallet.mission.dto.*;
import com.e201.kidswallet.mission.service.MissionService;
import com.e201.kidswallet.common.ResponseDto;
import com.e201.kidswallet.mission.transactional.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mission")
@Slf4j
public class MissionController {


    private final MissionService service;
    private final FcmService fcmService;
    private final TransactionService transactionService;

    @Autowired
    public MissionController(MissionService begsService, FcmService fcmService, TransactionService transactionService) {
        this.service = begsService;
        this.fcmService = fcmService;
        this.transactionService = transactionService;
    }

    //아이가 어른에게 조르기
    @PostMapping("/beg")
    public ResponseEntity<?> begging(@RequestBody BeggingRequestDto requestDto){
        StatusCode beggingResult = service.begging(requestDto);
        if(beggingResult != StatusCode.SUCCESS){
            return ResponseDto.response(beggingResult);
        }
        StatusCode sendMessageResult = fcmService.sendMessage(fcmService.getToken(requestDto.getToUserId()),
                                                                                "조르기가 도착했어요",
                                                                                "조르기가 도착했어요");
        if(sendMessageResult != StatusCode.SUCCESS){
            return ResponseDto.response(sendMessageResult);
        }
        return ResponseDto.response(StatusCode.SUCCESS);
    }

    // 조르기 수락 or 거절
    @PutMapping("/beg")
    public ResponseEntity<?> acceptBegging(@RequestBody BegAcceptRequestDto requestDto){
        log.info(requestDto.toString());
        long BegId = requestDto.getBegId();
        boolean isAccept = requestDto.getIsAccept();
        StatusCode begAcceptResult = service.begAccept(requestDto);
        if(begAcceptResult != StatusCode.SUCCESS){
            return ResponseDto.response(begAcceptResult);
        }

        long childId = service.UseBegIdGetChildId(requestDto.getBegId());
        StatusCode sendMessageResult;
        if(isAccept){
            sendMessageResult = fcmService.sendMessage(fcmService.getToken(childId),
                                                            "조르기를 수락했어요!",
                                                            "조르기를 수락했어요!");
        }
        else{
            sendMessageResult = fcmService.sendMessage(fcmService.getToken(childId),
                                                            "조르기를 거절했어요!",
                                                            "조르기를 거절했어요!");
        }
        if(sendMessageResult != StatusCode.SUCCESS){
            return ResponseDto.response(sendMessageResult);
        }

        return ResponseDto.response(StatusCode.SUCCESS);
    }

    // 미션 부여
    @PostMapping("/assign")
    public ResponseEntity<?> assignMission(@RequestBody AssignMissionRequestDto requestDto){
        StatusCode assignMissionResult = service.assignMission(requestDto);
        if(assignMissionResult != StatusCode.SUCCESS){
            return ResponseDto.response(assignMissionResult);
        }

        long childId = service.UseBegIdGetChildId(requestDto.getBegId());
        StatusCode sendMessageResult = fcmService.sendMessage(fcmService.getToken(childId),
                                                            "미션이 왔어요!",
                                                            "미션이 왔어요!");
        if(sendMessageResult != StatusCode.SUCCESS){
            return ResponseDto.response(sendMessageResult);
        }

        return ResponseDto.response(StatusCode.SUCCESS);
    }

    //부모에게 미션 수행 여부 전송
    @PutMapping("/complete")
    public ResponseEntity<?>missionComplete(@RequestBody MissionCompleteRequestDto requestDto){
        StatusCode uploadCompleteImageResult= service.uploadCompleteImage(requestDto);
        if(uploadCompleteImageResult != StatusCode.SUCCESS){
            return ResponseDto.response(uploadCompleteImageResult);
        }
        long parentId = service.UseMissionIdGetParentId(requestDto.getMissionId());
        StatusCode sendMessageResult = fcmService.sendMessage(fcmService.getToken(parentId),
                                                        "미션을 수행했어요!",
                                                        "용돈 주세요!");
        if(sendMessageResult != StatusCode.SUCCESS){
            return ResponseDto.response(sendMessageResult);
        }
        return ResponseDto.response(StatusCode.SUCCESS);
    }

    //부모에게 미션 수행 여부 판단
    @PutMapping("/complete/check")
    public ResponseEntity<?> checkMissionComplete(@RequestBody MissionCompleteCheckRequestDto requestDto){
        StatusCode transferResult = transactionService.transferMissionservice(requestDto);

        if(transferResult != StatusCode.SUCCESS){
            return ResponseDto.response(transferResult);
        }
        long childId = service.UseMissionIdGetChildId(requestDto.getMissionId());
        StatusCode sendMessageResult;

        if(requestDto.getIsComplete()){
           sendMessageResult = fcmService.sendMessage(fcmService.getToken(childId),
                                                            "미션 성공!",
                                                            "미션 완료!");
        }
        else{
            sendMessageResult = fcmService.sendMessage(fcmService.getToken(childId),
                                                                "미션 실패!",
                                                                "미션 실패!");
        }

        if(sendMessageResult != StatusCode.SUCCESS){
            return ResponseDto.response(sendMessageResult);
        }

        return ResponseDto.response(StatusCode.SUCCESS);
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<?> getBegMissionList(@PathVariable(name="userId") long userId, @RequestParam(name="start")long start, @RequestParam(name="end") long end){
//        return ResponseDto.response(service.getBegMissionList(userId, start, end));
        return ResponseDto.response(StatusCode.SUCCESS, service.getBegMissionList(userId, start, end));

    }

}
