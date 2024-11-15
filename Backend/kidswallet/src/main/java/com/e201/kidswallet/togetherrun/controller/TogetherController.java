package com.e201.kidswallet.togetherrun.controller;

import com.e201.kidswallet.common.ResponseDto;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.togetherrun.dto.*;
import com.e201.kidswallet.togetherrun.entity.SavingContract;
import com.e201.kidswallet.togetherrun.entity.TogetherRun;
import com.e201.kidswallet.togetherrun.entity.enums.TogetherRunStatus;
import com.e201.kidswallet.togetherrun.repository.SavingContractRepository;
import com.e201.kidswallet.togetherrun.repository.TogetherRunRepository;
import com.e201.kidswallet.togetherrun.service.TogetherRunService;
import com.e201.kidswallet.user.entity.User;
import com.e201.kidswallet.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto> postTogetherRunRegister(@Parameter(description = "JSON data for target") @RequestPart("json") TogetherRunRegisterRequestDto togetherRunRegisterRequestDto,
                                                               @Parameter(description = "File to upload") @RequestPart(value = "targetImage", required = false) MultipartFile targetImage) throws IOException {
        StatusCode returnCode = null;
        if (targetImage == null) {
            returnCode = togetherRunService.togetherRunRegister(togetherRunRegisterRequestDto, null);
        } else {
            returnCode = togetherRunService.togetherRunRegister(togetherRunRegisterRequestDto, targetImage);
        }
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
