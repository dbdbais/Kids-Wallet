package com.e201.kidswallet.togetherrun.service;

import com.e201.kidswallet.account.dto.TransferMoneyDTO;
import com.e201.kidswallet.account.entity.Account;
import com.e201.kidswallet.account.repository.AccountRepository;
import com.e201.kidswallet.account.service.AccountService;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.togetherrun.dto.*;
import com.e201.kidswallet.togetherrun.entity.Saving;
import com.e201.kidswallet.togetherrun.entity.SavingContract;
import com.e201.kidswallet.togetherrun.entity.SavingPayment;
import com.e201.kidswallet.togetherrun.entity.TogetherRun;
import com.e201.kidswallet.togetherrun.entity.enums.SavingContractPaymentCheck;
import com.e201.kidswallet.togetherrun.entity.enums.SavingContractStatus;
import com.e201.kidswallet.togetherrun.entity.enums.TogetherRunStatus;
import com.e201.kidswallet.togetherrun.repository.SavingContractRepository;
import com.e201.kidswallet.togetherrun.repository.SavingPaymentRepository;
import com.e201.kidswallet.togetherrun.repository.SavingRepository;
import com.e201.kidswallet.togetherrun.repository.TogetherRunRepository;
import com.e201.kidswallet.user.entity.Relation;
import com.e201.kidswallet.user.entity.User;
import com.e201.kidswallet.user.repository.RelationRepository;
import com.e201.kidswallet.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final AccountService accountService;
    private final RelationRepository relationRepository;
    private final AccountRepository accountRepository;

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public TogetherRunService(TogetherRunRepository togetherRunRepository, SavingRepository savingRepository,
                              SavingContractRepository savingContractRepository, SavingPaymentRepository savingPaymentRepository,
                              UserRepository userRepository, AccountService accountService, RelationRepository relationRepository, AccountRepository accountRepository) {
        this.togetherRunRepository = togetherRunRepository;
        this.savingRepository = savingRepository;
        this.savingContractRepository = savingContractRepository;
        this.savingPaymentRepository = savingPaymentRepository;
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.relationRepository = relationRepository;
        this.accountRepository = accountRepository;
    }

    public StatusCode togetherRunRegister(TogetherRunRegisterRequestDto togetherRunRegisterRequestDto)
            throws IOException {

        User user = userRepository.findById(togetherRunRegisterRequestDto.getChildId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid userId"));

        if (user.getRepresentAccountId() == null) {
            return StatusCode.NO_REPRESENTATIVE_ACCOUNT;
        }

        List<Relation> relationList = relationRepository.findRelation(togetherRunRegisterRequestDto.getChildId());
        Relation relation = null;
        for (Relation r : relationList) {
            if (r.getParent().getUserId() == togetherRunRegisterRequestDto.getParentsId()) {
                relation = r;
                break;
            }
        }

        if (relation == null) {
            return StatusCode.NO_PARENTS;
        }

        if (isCheckAccountBalance(togetherRunRegisterRequestDto.getChildId(), togetherRunRegisterRequestDto.getChildContribute())) {
            return StatusCode.NOT_ENOUGH_MONEY;
        }

        String imagePath = null;
        String urlPath = null;
        try {
            imagePath = saveImage(togetherRunRegisterRequestDto.getTargetImage());
            Path path = Paths.get(imagePath);
            urlPath = path.subpath(4, path.getNameCount()).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        User parent = userRepository.findById(togetherRunRegisterRequestDto.getParentsId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
        TogetherRun togetherRun = TogetherRun.builder()
                .relation(relation)
                .targetTitle(togetherRunRegisterRequestDto.getTargetTitle())
                .targetImage(urlPath)
                .parentsAccount(parent.getAccounts().get(0).getAccountId())
                .parentsContribute(togetherRunRegisterRequestDto.getParentsContribute())
                .childAccount(user.getAccounts().get(0).getAccountId())
                .childContribute(togetherRunRegisterRequestDto.getChildContribute())
                .targetAmount(togetherRunRegisterRequestDto.getTargetAmount())
                .targetDate(togetherRunRegisterRequestDto.getTargetDate())
                .build();

        // FCM notification
        try {
            togetherRunRepository.save(togetherRun);
            return StatusCode.SUCCESS;
        } catch (Exception e) {
            return StatusCode.BAD_REQUEST;
        }
    }

    public StatusCode togetherRunAnswer(Long togetherRunId, TogetherRunStatus isAccept, TogetherRunAnswerRequestDto togetherRunAnswerRequestDto) {
        TogetherRun togetherRun = togetherRunRepository.findById(togetherRunId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid togetherRunId"));
        togetherRun.setStatus(isAccept);
        try {
            togetherRunRepository.save(togetherRun);
        } catch (Exception e) {
            System.out.println("togetherRunRepository Error");
            return StatusCode.BAD_REQUEST;
        }
        User parents = userRepository.findById(togetherRunAnswerRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
        if (parents.getRepresentAccountId() == null) {
            return StatusCode.NO_REPRESENTATIVE_ACCOUNT;
        }
        SavingContract savingContract = null;
        User child = togetherRun.getRelation().getChild();
        if (isAccept == TogetherRunStatus.ACCEPTED) {
            if (isCheckAccountBalance(parents.getUserId(), togetherRun.getParentsContribute())) {
                return StatusCode.NOT_ENOUGH_MONEY;
            }

            try {
                String accountId = accountService.makeRandomAccount();
                Saving saving = savingRepository.findById((long)1).orElseThrow(() -> new IllegalArgumentException("Invalid savingId"));
                if (saving == null) {
                    return StatusCode.INVALID_SAVING;
                }
                Account newAccount = Account.builder()
                        .user(parents)
                        .accountId(accountId)
                        .build();
                accountRepository.save(newAccount);
                savingContract = SavingContract.builder()
                        .user(userRepository.findById(togetherRun.getRelation().getParent().getUserId())
                                .orElseThrow(() -> new IllegalArgumentException("Invalid userId")))
                        .saving(saving)
                        .savingAccount(accountId)
                        .depositDay((short)togetherRun.getCreatedAt().toLocalDate().getDayOfWeek().getValue())
                        .expiredAt(togetherRun.getTargetDate())
                        .build();
                savingContractRepository.save(savingContract);

                TransferMoneyDTO childTransferMoneyDTO = makeTransferMoneyDTO(togetherRun.getRelation().getChild().getRepresentAccountId(), accountId, togetherRun.getChildContribute().intValue());
                TransferMoneyDTO parentsTransferMoneyDTO = makeTransferMoneyDTO(parents.getRepresentAccountId(), accountId, togetherRun.getParentsContribute().intValue());

                StatusCode childTransferResult = null;
                StatusCode parentsTransferResult = null;
                childTransferResult = accountService.transferMoney(childTransferMoneyDTO);
                parentsTransferResult = accountService.transferMoney(parentsTransferMoneyDTO);
                if (childTransferResult.getHttpStatus() == StatusCode.BAD_REQUEST.getHttpStatus() || parentsTransferResult.getHttpStatus() == StatusCode.BAD_REQUEST.getHttpStatus()) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return StatusCode.TEST;
                }

                SavingPayment childPayment = SavingPayment.builder()
                        .user(child)
                        .depositAmount(togetherRun.getChildContribute())
                        .depositDate(LocalDateTime.now())
                        .savingContract(savingContract)
                        .build();
                SavingPayment parentsPayment = SavingPayment.builder()
                        .user(parents)
                        .depositAmount(togetherRun.getParentsContribute())
                        .depositDate(LocalDateTime.now())
                        .savingContract(savingContract)
                        .build();

                savingPaymentRepository.save(childPayment);
                savingPaymentRepository.save(parentsPayment);

            } catch (Exception e) {
                System.out.println("savingContractRepository Error");
                return StatusCode.BAD_REQUEST;
            }
        }

        try {
            togetherRun.setSavingContract(savingContract);
            togetherRunRepository.save(togetherRun);
        } catch (Exception e) {
            return StatusCode.BAD_REQUEST;
        }
        return StatusCode.SUCCESS;
    }

    public List<TogetherRunDataResponseDto> togetherRunList(Long userId) {
        List<Object[]> result = togetherRunRepository.findTogetherRunInfoByUserId(userId);
        List<TogetherRunDataResponseDto> togetherRunDataResponseDtoList = new ArrayList<>();
        for (Object[] obj : result) {
            TogetherRunDataResponseDto togetherRunDataResponseDto = TogetherRunDataResponseDto.builder()
                    .targetTitle((String) obj[0])
                    .targetAmount((BigDecimal) obj[1])
                    .dDay((Long) obj[2])
                    .isAccept(((Long) obj[3]) == 1L)
                    .build();
            togetherRunDataResponseDtoList.add(togetherRunDataResponseDto);
        }
        return togetherRunDataResponseDtoList;
    }

    public TogetherRunDetailResponseDto togetherRunDetail(Long togetherRunId) {
        TogetherRun togetherRun = togetherRunRepository.findById(togetherRunId).orElseThrow(() -> new IllegalArgumentException("Invalid togetherRunId"));
        TogetherRunDetailResponseDto togetherRunDetailResponseDto = null;
        SavingContract savingContract = togetherRun.getSavingContract();
        // 수락 전
        if (savingContract == null) {
            try {
                togetherRunDetailResponseDto = TogetherRunDetailResponseDto.builder()
                        .targetTitle(togetherRun.getTargetTitle())
                        .targetImage(togetherRun.getTargetImage())
                        .targetAmount(togetherRun.getTargetAmount())
                        .expiredAt(togetherRun.getTargetDate())
                        .dDay(LocalDate.now().compareTo(togetherRun.getCreatedAt().toLocalDate()))
                        .isAccept(togetherRun.getStatus() != TogetherRunStatus.PENDING)
                        .childGoalAmount(togetherRun.getChildContribute())
                        .childName(togetherRun.getRelation().getChild().getUserRealName())
                        .parentGoalAmount(togetherRun.getParentsContribute())
                        .parentName(togetherRun.getRelation().getParent().getUserRealName())
                        .build();
            } catch (Exception e) {
                return null;
            }
        // 수락 후
        } else {
            Long savingContractId = savingContract.getSavingContractId();
            try {
                togetherRunDetailResponseDto = TogetherRunDetailResponseDto.builder()
                        .targetTitle(togetherRun.getTargetTitle())
                        .targetImage(togetherRun.getTargetImage())
                        .targetAmount(togetherRun.getTargetAmount())
                        .expiredAt(savingContract.getExpiredAt())
                        .dDay(LocalDate.now().compareTo(savingContract.getExpiredAt()))
                        .isAccept(togetherRun.getStatus() != TogetherRunStatus.PENDING)
                        .childAmount(savingPaymentRepository.findTotalDepositAmountBySavingContractIdAndUserId(savingContract.getSavingContractId(), togetherRun.getRelation().getChild().getUserId()))
                        .childGoalAmount(togetherRun.getChildContribute())
                        .childName(togetherRun.getRelation().getChild().getUserRealName())
                        .parentAmount(savingPaymentRepository.findTotalDepositAmountBySavingContractIdAndUserId(savingContract.getSavingContractId(), togetherRun.getRelation().getParent().getUserId()))
                        .parentGoalAmount(togetherRun.getParentsContribute())
                        .parentName(togetherRun.getRelation().getParent().getUserRealName())
                        .build();
            } catch (Exception e) {
                return null;
            }
        }

        return togetherRunDetailResponseDto;
    }

    public TogetherRunCancelResponseDto togetherRunDelete(Long savingContractId) {
        SavingContract savingContract = savingContractRepository.findById(savingContractId).orElseThrow(() -> new IllegalArgumentException("Invalid SavingContractId"));
        savingContract.setStatus(SavingContractStatus.CANCELED);
        savingContract.setDeletedAt(LocalDateTime.now());
        TogetherRunCancelResponseDto togetherRunCancelResponseDto = null;
        try {
            savingContractRepository.save(savingContract);
            togetherRunCancelResponseDto = TogetherRunCancelResponseDto.builder()
                    .cancelAmount(savingContract.getCurrentInterestAmount())
                    .build();
            return togetherRunCancelResponseDto;
        } catch (Exception e) {

            return togetherRunCancelResponseDto;
        }
    }

    public StatusCode togetherRunComplete(SavingContract savingContract) {

        try {
            savingContractRepository.save(savingContract);
        } catch (Exception e) {
            return StatusCode.BAD_REQUEST;
        }
        return StatusCode.SUCCESS;
    }

    public String saveImage(MultipartFile file) throws IOException {
        String appPath = "/src/main/resources/static/uploads/";
        String systemPath = System.getProperty("user.dir") + appPath;
        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + "." + originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
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

    public BigDecimal calcInterestAmount(SavingContract savingContract) {
        Saving saving = savingRepository.findById(savingContract.getSaving().getSavingId()).orElse(null);
        BigDecimal interestRate = saving.getInterestRate();

        LocalDate startDate = savingContract.getCreatedAt().toLocalDate();
        int days = LocalDate.now().compareTo(startDate);

        BigDecimal amount = savingContract.getCurrentAmount();

        BigDecimal  interestAmount = amount.multiply(interestRate).divide(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(365), 2).multiply(BigDecimal.valueOf(days));
        return interestAmount;
    }

    public Boolean isCheckAccountBalance(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
        Account account = accountRepository.getReferenceById(user.getRepresentAccountId());
        return account.getBalance() - amount.intValue() < 0;
    }

    public TransferMoneyDTO makeTransferMoneyDTO(String fromId, String toId, int amount) {
        TransferMoneyDTO transferMoneyDTO = null;
        transferMoneyDTO = TransferMoneyDTO.builder()
                .fromId(fromId)
                .toId(toId)
                .amount(amount)
                .build();
        return transferMoneyDTO;
    }
}
