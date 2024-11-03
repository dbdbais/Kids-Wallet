package com.e201.kidswallet.mission.controller;

import com.e201.kidswallet.mission.dto.AssignMissionRequestDto;
import com.e201.kidswallet.mission.dto.BegAcceptRequestDto;
import com.e201.kidswallet.mission.dto.BeggingRequestDto;
import com.e201.kidswallet.mission.dto.MissionCompleteRequestDto;
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

    //TODO: 어느 어른에게 조르기할지 수정
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

    @PutMapping("/complete")
    public ResponseEntity<?>missionComplete(@RequestBody MissionCompleteRequestDto requestDto){
        return ResponseDto.response(service.uploadCompleteImage(requestDto));
    }

}
