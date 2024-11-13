package com.e201.kidswallet.togetherrun.controller;

import com.e201.kidswallet.common.ResponseDto;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.togetherrun.dto.TogetherRunAnswerRequestDto;
import com.e201.kidswallet.togetherrun.dto.TogetherRunDataResponseDto;
import com.e201.kidswallet.togetherrun.dto.TogetherRunDetailResponseDto;
import com.e201.kidswallet.togetherrun.dto.TogetherRunRegisterRequestDto;
import com.e201.kidswallet.togetherrun.entity.enums.TogetherRunStatus;
import com.e201.kidswallet.togetherrun.service.TogetherRunService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/togetherrun")
public class TogetherController {

    private final TogetherRunService togetherRunService;
    private final ObjectMapper objectMapper;

    @Autowired
    public TogetherController(TogetherRunService togetherRunService, ObjectMapper objectMapper) {
        this.togetherRunService = togetherRunService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> postTogetherRunRegister(@RequestParam("json") String jsonData,
            @RequestParam("targetImage") MultipartFile targetImage) throws IOException {
        TogetherRunRegisterRequestDto togetherRunRegisterRequestDto = objectMapper.readValue(jsonData,
                TogetherRunRegisterRequestDto.class);
        togetherRunRegisterRequestDto.setTargetImage(targetImage);
        StatusCode returnCode = togetherRunService.togetherRunRegister(togetherRunRegisterRequestDto);
        return ResponseDto.response(returnCode);
    }

    @PostMapping("{togetherRunId}/accept")
    public ResponseEntity<ResponseDto> postTogetherRunAccept(@PathVariable("togetherRunId") long togetherRunId, @RequestBody TogetherRunAnswerRequestDto togetherRunAnswerRequestDto) {
        StatusCode returnCode = togetherRunService.togetherRunAnswer(togetherRunId, TogetherRunStatus.ACCEPTED, togetherRunAnswerRequestDto);
        return ResponseDto.response(returnCode);
    }

    @PostMapping("{togetherRunId}/reject")
    public ResponseEntity<ResponseDto> postTogetherRunReject(@PathVariable("togetherRunId") long togetherRunId, @RequestBody TogetherRunAnswerRequestDto togetherRunAnswerRequestDto) {
        StatusCode returnCode = togetherRunService.togetherRunAnswer(togetherRunId, TogetherRunStatus.REJECTED, togetherRunAnswerRequestDto);
        return ResponseDto.response(returnCode);
    }

    @GetMapping("{userId}/list")
    public ResponseEntity<ResponseDto> getTogetherRunList(@PathVariable("userId") long userId) {
        List<TogetherRunDataResponseDto> togetherRunDataResponseDto = togetherRunService.togetherRunList(userId);
        if (togetherRunDataResponseDto == null) {
            return ResponseDto.response(StatusCode.BAD_REQUEST);
        } else {
            return ResponseDto.response(StatusCode.SUCCESS, togetherRunService.togetherRunList(userId));
        }
    }

    @GetMapping("{savingContractId}/detail")
    public ResponseEntity<ResponseDto> getTogetherRunDetail(@PathVariable("savingContractId") long savingContractId) {
        TogetherRunDetailResponseDto togetherRunDetailResponseDto = togetherRunService.togetherRunDetail(savingContractId);
        if (togetherRunDetailResponseDto == null) {
            return ResponseDto.response(StatusCode.BAD_REQUEST);
        } else {
            return ResponseDto.response(StatusCode.SUCCESS, togetherRunService.togetherRunDetail(savingContractId));
        }
    }

    @DeleteMapping("savings/{savingContractId}")
    public ResponseEntity<ResponseDto> deleteTogetherRun(@PathVariable("savingContractId") long savingContractId) {
        return ResponseDto.response(StatusCode.SUCCESS, togetherRunService.togetherRunDelete(savingContractId));
    }
}
