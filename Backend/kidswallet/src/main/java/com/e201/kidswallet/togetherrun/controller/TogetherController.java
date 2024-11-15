package com.e201.kidswallet.togetherrun.controller;

import com.e201.kidswallet.common.ResponseDto;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.togetherrun.dto.TogetherRunAnswerRequestDto;
import com.e201.kidswallet.togetherrun.dto.TogetherRunRegisterRequestDto;
import com.e201.kidswallet.togetherrun.entity.SavingContract;
import com.e201.kidswallet.togetherrun.entity.TogetherRun;
import com.e201.kidswallet.togetherrun.entity.enums.TogetherRunStatus;
import com.e201.kidswallet.togetherrun.repository.SavingContractRepository;
import com.e201.kidswallet.togetherrun.repository.TogetherRunRepository;
import com.e201.kidswallet.togetherrun.service.TogetherRunService;
import com.e201.kidswallet.user.entity.User;
import com.e201.kidswallet.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/togetherrun")
public class TogetherController {

    private final TogetherRunService togetherRunService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final SavingContractRepository savingContractRepository;
    private final TogetherRunRepository togetherRunRepository;

    @Autowired
    public TogetherController(TogetherRunService togetherRunService, UserRepository userRepository, ObjectMapper objectMapper, SavingContractRepository savingContractRepository, TogetherRunRepository togetherRunRepository) {
        this.togetherRunService = togetherRunService;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.savingContractRepository = savingContractRepository;
        this.togetherRunRepository = togetherRunRepository;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDto> postTogetherRunRegister(@RequestBody TogetherRunRegisterRequestDto togetherRunRegisterRequestDto) throws IOException {
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
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseDto.response(StatusCode.NO_USER);
        }
        return ResponseDto.response(StatusCode.SUCCESS, togetherRunService.togetherRunList(userId));
    }

    @GetMapping("{togetherRunId}/detail")
    public ResponseEntity<ResponseDto> getTogetherRunDetail(@PathVariable("togetherRunId") long togetherRunId) {
        TogetherRun togetherRun = togetherRunRepository.findById(togetherRunId).orElse(null);
        if (togetherRun == null) {
            return ResponseDto.response(StatusCode.INVALID_TOGETHERRUN);
        }
        return ResponseDto.response(StatusCode.SUCCESS, togetherRunService.togetherRunDetail(togetherRunId));
    }

    @DeleteMapping("savings/{savingContractId}")
    public ResponseEntity<ResponseDto> deleteTogetherRun(@PathVariable("savingContractId") long savingContractId) {
        SavingContract savingContract = savingContractRepository.findById(savingContractId).orElse(null);
        if (savingContract == null) {
            return ResponseDto.response(StatusCode.INVALID_SAVING_CONTRACT);
        }
        return ResponseDto.response(StatusCode.SUCCESS, togetherRunService.togetherRunCancel(savingContractId));
    }
}
