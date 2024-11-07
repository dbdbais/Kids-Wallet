package com.e201.kidswallet.togetherrun.service;

import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.togetherrun.dto.TogetherRunCancelResponseDto;
import com.e201.kidswallet.togetherrun.dto.TogetherRunDataResponseDto;
import com.e201.kidswallet.togetherrun.dto.TogetherRunDetailResponseDto;
import com.e201.kidswallet.togetherrun.dto.TogetherRunRegisterRequestDto;
import com.e201.kidswallet.togetherrun.entity.*;
import com.e201.kidswallet.togetherrun.repository.SavingContractRepository;
import com.e201.kidswallet.togetherrun.repository.SavingPaymentRepository;
import com.e201.kidswallet.togetherrun.repository.SavingRepository;
import com.e201.kidswallet.togetherrun.repository.TogetherRunRepository;
import com.e201.kidswallet.user.entity.Relation;
import com.e201.kidswallet.user.entity.User;
import com.e201.kidswallet.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TogetherRunService {

    private final TogetherRunRepository togetherRunRepository;
    private final SavingRepository savingRepository;
    private final SavingContractRepository savingContractRepository;
    private final SavingPaymentRepository savingPaymentRepository;
    private final UserRepository userRepository;

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public TogetherRunService(TogetherRunRepository togetherRunRepository, SavingRepository savingRepository,
            SavingContractRepository savingContractRepository, SavingPaymentRepository savingPaymentRepository,
            UserRepository userRepository) {
        this.togetherRunRepository = togetherRunRepository;
        this.savingRepository = savingRepository;
        this.savingContractRepository = savingContractRepository;
        this.savingPaymentRepository = savingPaymentRepository;
        this.userRepository = userRepository;
    }

    public StatusCode togetherRunRegister(TogetherRunRegisterRequestDto togetherRunRegisterRequestDto)
            throws IOException {

        User user = userRepository.findById(togetherRunRegisterRequestDto.getParentsId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
        List<Relation> relationList = user.getChildsRelations();
        Relation relation = null;
        for (Relation r : relationList) {
            if (r.getChild().getUserId() == togetherRunRegisterRequestDto.getChildId()) {
                relation = r;
                break;
            }
        }

        String imagePath = null;
        try {
            imagePath = saveImage(togetherRunRegisterRequestDto.getTargetImage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        TogetherRun togetherRun = TogetherRun.builder()
                .relation(relation)
                .targetImage(imagePath)
                .parentsAccount("123")
                .parentsContribute(togetherRunRegisterRequestDto.getParentsContribute())
                .childAccount("123")
                .childContribute(togetherRunRegisterRequestDto.getChildContribute())
                .targetAmount(togetherRunRegisterRequestDto.getTargetAmount())
                .targetDate(togetherRunRegisterRequestDto.getTargetDate())
                .build();

        // return redisTemplate.opsForHash().entries();
        // FCM notification
        try {
            togetherRunRepository.save(togetherRun);
            return StatusCode.SUCCESS;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return StatusCode.BAD_REQUEST;
        }
    }

    public StatusCode togetherRunAnswer(Long togetherRunId, TogetherRunStatus isAccept) {
        TogetherRun togetherRun = togetherRunRepository.findById(togetherRunId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid togetherRunId"));
        togetherRun.setStatus(isAccept);
        try {
            togetherRunRepository.save(togetherRun);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return StatusCode.BAD_REQUEST;
        }
        // 적금 생성
        // 반환
        return StatusCode.SUCCESS;
    }

    public List<TogetherRunDataResponseDto> togetherRunList(Long userId) {
        List<TogetherRunDataResponseDto> togetherRunDataResponseDtoList = togetherRunRepository.findTogetherRunInfoByUserId(userId);
        return togetherRunDataResponseDtoList;
    }

    public List<TogetherRunDetailResponseDto> togetherRunDetail(Long SavingContractId) {
        SavingContract savingContract = savingContractRepository.findById(SavingContractId).orElseThrow(() -> new IllegalArgumentException("Invalid SavingContractId"));
        List<SavingPayment> savingPayment = savingContract.getSavingPayments();
        List<TogetherRunDetailResponseDto> togetherRunDetailResponseDtoList = new ArrayList<>();
        User user = null;
        for (SavingPayment payment : savingPayment) {
            TogetherRunDetailResponseDto togetherRunDetailResponseDto = TogetherRunDetailResponseDto.builder()
                    .userRealName(payment.getUser().getUserRealName())
                    .currentAmount(payment.getSavingContract().getCurrentAmount())
                    .depositAmount(payment.getDepositAmount())
                    .depositedAt(payment.getDepositDate().atStartOfDay())
                    .build();
            togetherRunDetailResponseDtoList.add(togetherRunDetailResponseDto);
        }
        return togetherRunDetailResponseDtoList;
    }

    public TogetherRunCancelResponseDto togetherRunDelete(Long savingContractId) {
        SavingContract savingContract = savingContractRepository.findById(savingContractId).orElseThrow(() -> new IllegalArgumentException("Invalid SavingContractId"));
        savingContract.setStatus(SavingContractStatus.CANCELED);
        // 종료 날짜 입력
        TogetherRunCancelResponseDto togetherRunCancelResponseDto = null;
        try {
            savingContractRepository.save(savingContract);
            // 해지액 계산
            togetherRunCancelResponseDto = TogetherRunCancelResponseDto.builder()
                    .cancelAmount(BigDecimal.valueOf(123))
                    .build();
            return togetherRunCancelResponseDto;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return togetherRunCancelResponseDto;
        }
    }

    public String saveImage(MultipartFile file) throws IOException {
        String appPath = "/src/main/resources/static/uploads/";
        String systemPath = System.getProperty("user.dir") + appPath;
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(systemPath + fileName);

        // 디렉토리 생성 여부 확인 및 생성
        if (Files.notExists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        // 파일 저장
        Files.createDirectories(filePath.getParent());
        file.transferTo(filePath.toFile());

        return appPath + fileName;
    }
}
