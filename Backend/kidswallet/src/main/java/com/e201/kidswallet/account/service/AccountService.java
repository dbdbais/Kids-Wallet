package com.e201.kidswallet.account.service;

import com.e201.kidswallet.account.dto.MonthlyExpenseDTO;
import com.e201.kidswallet.account.dto.TransactionListDTO;
import com.e201.kidswallet.account.dto.TransactionResponseDTO;
import com.e201.kidswallet.account.dto.TransferMoneyDTO;
import com.e201.kidswallet.account.entity.Account;
import com.e201.kidswallet.account.repository.AccountRepository;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.transaction.entity.Transaction;
import com.e201.kidswallet.transaction.enums.TransactionType;
import com.e201.kidswallet.transaction.repository.TransactionRepository;
import com.e201.kidswallet.user.entity.User;
import com.e201.kidswallet.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public String makeRandomAccount() {
        SecureRandom secureRandom = new SecureRandom();

        // 앞 6자리 코드
        int bankCode = secureRandom.nextInt(900000) + 100000; // 100000 ~ 999999 범위의 숫자 생성

        // 중간 2자리 코드
        int accountType = secureRandom.nextInt(90) + 10; // 100000 ~ 999999 범위의 숫자 생성

        // 마지막 6자리 무작위 숫자
        int uniqueNumber = secureRandom.nextInt(900000) + 100000; // 100000 ~ 999999 범위의 숫자 생성

        // 형식에 맞춰 계좌 번호 생성
        return String.format("%06d-%02d-%06d", bankCode, accountType, uniqueNumber);
    }

    public StatusCode depositMoney(String accountId, int amount) {
        Optional<Account> account = accountRepository.findById(accountId);

        if (account.isEmpty()) {
            return StatusCode.BAD_REQUEST;
        }

        int curAmount = account.get().getBalance() + amount;

        // 해당하는 트랙잭션 생성
        Transaction depositTransaction = makeTransaction(TransactionType.DEPOSIT, "입금", accountId, amount,curAmount);

        // 트랜잭션을 먼저 저장
        transactionRepository.save(depositTransaction);


        account.get().deposit(amount);
        account.get().addTransaction(depositTransaction);

        account.get().getUser().depositMoney(amount);
        //유저에게도 반영

        return StatusCode.SUCCESS;

    }

    public StatusCode withdrawMoney(String accountId, int amount) {
        Optional<Account> account = accountRepository.findById(accountId);

        if (account.isEmpty()) {
            return StatusCode.BAD_REQUEST;
        }

        if (account.get().getBalance() < amount) {
            return StatusCode.NOT_ENOUGH_MONEY;
        }
        int curAmount = account.get().getBalance() - amount;
        // 해당하는 트랙잭션 생성
        Transaction withdrawlTransaction = makeTransaction(TransactionType.WITHDRAWAL, "인출", accountId, amount,curAmount);

        transactionRepository.save(withdrawlTransaction);

        account.get().withdraw(amount);
        account.get().addTransaction(withdrawlTransaction);
        //계좌 출금

        account.get().getUser().withdrawMoney(amount);
        // 유저에게도 반영

        return StatusCode.SUCCESS;
    }

    private Transaction makeTransaction(TransactionType transactionType, String message, String id, int amount,int curAmount) {

        return Transaction.builder()
                .transactionType(transactionType)
                .message(message)
                .accountId(id)
                .amount(amount)
                .curAmount(curAmount)
                .build();
    }

    @Transactional
    public StatusCode transferMoney(TransferMoneyDTO transferMoneyDTO) {

        String fromAccountId = transferMoneyDTO.getFromId();
        String toAccountId = transferMoneyDTO.getToId();
        int amount = transferMoneyDTO.getAmount();
        String message = transferMoneyDTO.getMessage();

        Optional<Account> fAccount = accountRepository.findById(fromAccountId);
        Optional<Account> tAccount = accountRepository.findById(toAccountId);

        if(fAccount.isEmpty() || tAccount.isEmpty()){
            return StatusCode.BAD_REQUEST;
        }

        if (fAccount.get().getBalance() < amount) {
            return StatusCode.NOT_ENOUGH_MONEY;
        }

        int wAmount = fAccount.get().getBalance() - amount;
        int fAmount = tAccount.get().getBalance() + amount;

        // 해당하는 트랙잭션 생성
        Transaction withdrawalTransaction = makeTransaction(TransactionType.WITHDRAWAL,  (message == null) ? tAccount.get().getUser().getUserName() : message, toAccountId, amount,wAmount);
        Transaction depositTransaction = makeTransaction(TransactionType.DEPOSIT, (message == null) ? fAccount.get().getUser().getUserName() : message, fromAccountId, amount,fAmount);

        // 트랜잭션을 먼저 저장
        transactionRepository.save(withdrawalTransaction);
        transactionRepository.save(depositTransaction);

        // 출금 및 입금 작업 트랜잭션 추가 수행
        fAccount.get().withdraw(amount);
        fAccount.get().addTransaction(withdrawalTransaction);
        // 유저에게도 반영
        fAccount.get().getUser().withdrawMoney(amount);

        tAccount.get().deposit(amount);
        tAccount.get().addTransaction(depositTransaction);

        // 유저에게도 반영
        tAccount.get().getUser().depositMoney(amount);

        // 변경 사항 저장
        accountRepository.save(fAccount.get());
        accountRepository.save(tAccount.get());

        return StatusCode.SUCCESS;
    }

    public TransactionListDTO getTransaction(String accountId){

        Optional<Account> account = accountRepository.findById(accountId);

        if(account.isEmpty()){
            return new TransactionListDTO(StatusCode.BAD_REQUEST);
        }


        List<TransactionResponseDTO> lst = account.get().getTransactions().stream()
                .map(t -> TransactionResponseDTO.builder()
                        .accountId(t.getAccountId())
                        .message(t.getMessage())
                        .amount(t.getAmount())
                        .transactionType(t.getTransactionType())
                        .transactionDate(t.getTransactionDate())
                        .curBalance(t.getCurAmount())
                        .build())
                .collect(Collectors.toList());

        //내림차순으로 정렬해서 던짐
        Collections.reverse(lst);

        //페이징 처리도 하면 좋을 듯?

        return new TransactionListDTO(StatusCode.SUCCESS,lst);
    }

    public StatusCode registerAccount(Long userId) {

        Optional<User> sUser = userRepository.findById(userId);

        if (sUser.isEmpty()) {
            return StatusCode.BAD_REQUEST;
        }

        String newAccountId = makeRandomAccount();

        while (accountRepository.existsById(newAccountId)) {
            newAccountId = makeRandomAccount();
        }
        //중복 체크

        Account newAccount = Account.builder()
                .user(sUser.get())
                .accountId(newAccountId)
                .build();

        sUser.get().getAccounts().add(newAccount);
        //유저에 추가
        //accountRepository.save(newAccount);
        sUser.get().setRepresentAccountId(newAccountId);
        //계좌 초기화
        sUser.get().initAccount();
        return StatusCode.SUCCESS;
    }

    public MonthlyExpenseDTO getMonthlyExpense(String accountId){
        Optional<Account> sAccount = accountRepository.findById(accountId);

        if(sAccount.isEmpty())
        {
            return null;
        }

        List<Transaction> transactions = sAccount.get().getTransactions();


        // 이번 주 월요일 날짜 계산
        LocalDate today = LocalDate.now();
        LocalDate mondayOfThisWeek = today.with(DayOfWeek.MONDAY);

        // 7일 전 날짜 및 이전 주 월요일 날짜 계산
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        LocalDate mondayOfLastWeek = mondayOfThisWeek.minusWeeks(1);

        // 7일 내 트랜잭션 필터링
        List<Transaction> recentTransactions = transactions.stream()
                .filter(transaction -> transaction.getTransactionDate().toLocalDate().isAfter(sevenDaysAgo) &&
                        transaction.getTransactionDate().toLocalDate().isBefore(mondayOfThisWeek.plusDays(7)))
                .collect(Collectors.toList());


        // 금액 계산 (예시로 입금과 출금 구분)
        int totDeposit = 0;
        int totWithdraw = 0;
        int pTotDeposit = 0;
        int pTotWithdraw = 0;
        // 현재 주, 이전 주 금액을 일별로 저장
        int[] curListSpent = new int[7];  // 현재 주 지출 금액
        int[] curListIncome = new int[7]; // 현재 주 수입 금액
        int[] prevListSpent = new int[7];  // 현재 주 지출 금액
        int[] prevListIncome = new int[7]; // 현재 주 수입 금액

        // 트랜잭션을 날짜별로 그룹화
        Map<LocalDate, List<Transaction>> dailyTransactions = recentTransactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getTransactionDate().toLocalDate()));

        // 트랜잭션을 각 날짜별로 처리
        for (Map.Entry<LocalDate, List<Transaction>> entry : dailyTransactions.entrySet()) {
            LocalDate date = entry.getKey();
            List<Transaction> dayTransactions = entry.getValue();

            // 현재 주와 이전 주 날짜 확인
            int dayIndexForCurrentWeek = (int) ChronoUnit.DAYS.between(mondayOfThisWeek, date);
            int dayIndexForLastWeek = (int) ChronoUnit.DAYS.between(mondayOfLastWeek, date);

            for (Transaction transaction : dayTransactions) {
                if (transaction.getTransactionType() == TransactionType.DEPOSIT) {
                    if (dayIndexForCurrentWeek >= 0 && dayIndexForCurrentWeek < 7) {
                        totDeposit += transaction.getAmount();
                        curListIncome[dayIndexForCurrentWeek] += transaction.getAmount();  // 현재 주 수입 추가
                    } else if (dayIndexForLastWeek >= 0 && dayIndexForLastWeek < 7) {
                        prevListIncome[dayIndexForLastWeek] += transaction.getAmount();
                        pTotDeposit += transaction.getAmount(); // 이전 주 수입 추가
                    }
                } else if (transaction.getTransactionType() == TransactionType.WITHDRAWAL) {
                    if (dayIndexForCurrentWeek >= 0 && dayIndexForCurrentWeek < 7) {
                        totWithdraw += transaction.getAmount();
                        curListSpent[dayIndexForCurrentWeek] += transaction.getAmount();  // 현재 주 지출 추가
                    } else if (dayIndexForLastWeek >= 0 && dayIndexForLastWeek < 7) {
                        prevListSpent[dayIndexForLastWeek] += transaction.getAmount();  // 이전 주 지출 추가
                        pTotWithdraw += transaction.getAmount();  // 이전 주 총 출금 추가
                    }
                }
            }
        }

        // DTO 반환
        return MonthlyExpenseDTO.builder()
                .curSpentMoney(totWithdraw)
                .curIncomeMoney(totDeposit)
                .prevSpentMoney(pTotWithdraw)
                .prevIncomeMoney(pTotDeposit)
                .curListSpent(curListSpent)       // 현재 주 지출 금액
                .curListIncome(curListIncome)     // 현재 주 수입 금액
                .prevListSpent(prevListSpent)     // 이전 주 지출 금액
                .prevListIncome(prevListIncome)   // 이전 주 수입 금액
                .build();

    }

}
