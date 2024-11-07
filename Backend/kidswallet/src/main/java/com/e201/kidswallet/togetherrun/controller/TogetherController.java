package com.e201.kidswallet.togetherrun.controller;

import com.e201.kidswallet.common.ResponseDto;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.togetherrun.entity.TogetherRunStatus;
import com.e201.kidswallet.togetherrun.dto.TogetherRunAnswerRequestDto;
import com.e201.kidswallet.togetherrun.dto.TogetherRunRegisterRequestDto;
import com.e201.kidswallet.togetherrun.service.TogetherRunService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/togetherrun")
public class TogetherController {

    private final TogetherRunService togetherRunService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TogetherController(TogetherRunService togetherRunService) {
        this.togetherRunService = togetherRunService;
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
    public ResponseEntity<ResponseDto> postTogetherRunAccept(@PathVariable("togetherRunId") long togetherRunId) {
        StatusCode returnCode = togetherRunService.togetherRunAnswer(togetherRunId, TogetherRunStatus.ACCEPTED);
        return ResponseDto.response(returnCode);
    }

    @PostMapping("{togetherRunId}/reject")
    public ResponseEntity<ResponseDto> postTogetherRunReject(@PathVariable("togetherRunId") long togetherRunId) {
        StatusCode returnCode = togetherRunService.togetherRunAnswer(togetherRunId, TogetherRunStatus.REJECTED);
        return ResponseDto.response(returnCode);
    }

    @GetMapping("{userId}/list")
    public ResponseEntity<ResponseDto> getTogetherRunList(@PathVariable("userId") long userId) {
        return ResponseDto.response(StatusCode.SUCCESS, togetherRunService.togetherRunList(userId));
    }

    @GetMapping("{savingContractId}/detail")
    public ResponseEntity<ResponseDto> getTogetherRunDetail(@PathVariable("savingContractId") long savingContractId) {
        return ResponseDto.response(StatusCode.SUCCESS, togetherRunService.togetherRunDetail(savingContractId));
    }

    @DeleteMapping("savings/{savingContractId}")
    public ResponseEntity<ResponseDto> deleteTogetherRun(@PathVariable("savingContractId") long savingContractId) {
        return ResponseDto.response(StatusCode.SUCCESS, togetherRunService.togetherRunDelete(savingContractId));
    }
}
