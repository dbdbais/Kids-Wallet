package com.e201.kidswallet.account.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        account.get().deposit(amount);

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

        account.get().withdraw(amount);
        //계좌 출금

        return StatusCode.SUCCESS;

    }

    private Transaction makeTransaction(TransactionType transactionType, String message, String id, int amount) {

        return Transaction.builder()
                .transactionType(transactionType)
                .message(message)
                .accountId(id)
                .amount(amount)
                .build();
    }

    @Transactional
    public StatusCode transferMoney(TransferMoneyDTO transferMoneyDTO) {

        String fromAccountId = transferMoneyDTO.getFAccountId();
        String toAccountId = transferMoneyDTO.getTAcoountId();
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

        // 해당하는 트랙잭션 생성
        Transaction withdrawalTransaction = makeTransaction(TransactionType.WITHDRAWAL, message, toAccountId, amount);
        Transaction depositTransaction = makeTransaction(TransactionType.DEPOSIT, message, fromAccountId, amount);

        // 트랜잭션을 먼저 저장
        transactionRepository.save(withdrawalTransaction);
        transactionRepository.save(depositTransaction);

        // 출금 및 입금 작업 트랜잭션 추가 수행
        fAccount.get().withdraw(amount);
        fAccount.get().addTransaction(withdrawalTransaction);

        tAccount.get().deposit(amount);
        tAccount.get().addTransaction(depositTransaction);

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
                        .build())
                .collect(Collectors.toList());


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

        return StatusCode.SUCCESS;
    }


}
