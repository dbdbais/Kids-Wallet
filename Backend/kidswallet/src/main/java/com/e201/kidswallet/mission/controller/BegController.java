package com.e201.kidswallet.mission.controller;

import com.e201.kidswallet.mission.dto.BeggingRequestDto;
import com.e201.kidswallet.mission.service.BegService;
import com.e201.kidswallet.common.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mission")
public class BegController {


    private final BegService begsService;

    @Autowired
    public BegController(BegService begsService) {
        this.begsService = begsService;
    }

    @PostMapping("/beg")
    public ResponseEntity<?> begging(@RequestBody BeggingRequestDto requestDto){
        return ResponseDto.response(begsService.begging(requestDto));
    }


}