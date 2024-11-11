package com.e201.kidswallet.fcm.controller;

import com.e201.kidswallet.common.ResponseDto;
import com.e201.kidswallet.fcm.dto.FcmTokenRequestDto;
import com.e201.kidswallet.fcm.service.FcmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/fcm")
public class FcmController {

    FcmService fcmService;

    @Autowired
    public FcmController(FcmService fcmService) {
        this.fcmService = fcmService;
    }

    @PostMapping("/token")
    public ResponseEntity<?> fcm(@RequestBody FcmTokenRequestDto requestDto) {
        log.info("fcm request: {}", requestDto);
        return ResponseDto.response(fcmService.tokenToRedis(requestDto));
    }
}
