package com.e201.kidswallet.mission.controller;

import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.mission.dto.*;
import com.e201.kidswallet.mission.service.MissionService;
import com.e201.kidswallet.common.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mission")
@Slf4j
public class MissionController {


    private final MissionService service;

    @Autowired
    public MissionController(MissionService begsService) {
        this.service = begsService;
    }

    //아이가 어른에게 조르기
    @PostMapping("/beg")
    public ResponseEntity<?> begging(@RequestBody BeggingRequestDto requestDto){
        return ResponseDto.response(service.begging(requestDto));
    }

    // 조르기 수락 or 거절
    @PutMapping("/beg")
    public ResponseEntity<?> acceptBegging(@RequestBody BegAcceptRequestDto requestDto){
        log.info(requestDto.toString());
        return ResponseDto.response(service.begAccept(requestDto));
    }

    // 미션 부여
    @PostMapping("/assign")
    public ResponseEntity<?> assignMission(@RequestBody AssignMissionRequestDto requestDto){
        return ResponseDto.response(service.assignMission(requestDto));
    }

    //부모에게 미션 수행 여부 전송
    @PutMapping("/complete")
    public ResponseEntity<?>missionComplete(@RequestBody MissionCompleteRequestDto requestDto){
        return ResponseDto.response(service.uploadCompleteImage(requestDto));
    }

    //부모에게 미션 수행 여부 판단
    @PutMapping("/complete/check")
    public ResponseEntity<?> checkMissionComplete(@RequestBody MissionCompleteCheckRequestDto requestDto){
        return ResponseDto.response(service.missionCompleteCheck(requestDto));
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<?> getBegMissionList(@PathVariable(name="userId") long userId, @RequestParam(name="start")long start, @RequestParam(name="end") long end){
//        return ResponseDto.response(service.getBegMissionList(userId, start, end));
        return ResponseDto.response(StatusCode.SUCCESS, service.getBegMissionList(userId, start, end));

    }

}
