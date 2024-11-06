package com.e201.kidswallet.account.service;

import com.e201.kidswallet.account.entity.Account;
import com.e201.kidswallet.account.repository.AccountRepository;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.transaction.repository.TransactionRepository;
import com.e201.kidswallet.user.entity.User;
import com.e201.kidswallet.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.SecureRandom;

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

    public String makeRandomAccount(){
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

    public StatusCode depositMoney(String accountId,int amount){
        Account account = accountRepository.findById(accountId).orElse(null);

        if(account == null){
            return StatusCode.BAD_REQUEST;
        }

        account.deposit(amount);

        return StatusCode.SUCCESS;

    }

    public StatusCode withdrawMoney(String accountId, int amount){
        Account account = accountRepository.findById(accountId).orElse(null);

        if(account == null){
            return StatusCode.BAD_REQUEST;
        }

        if(account.getBalance() < amount){
            return StatusCode.NOT_ENOUGH_MONEY;
        }

        account.withdraw(amount);
        //계좌 출금

        return StatusCode.SUCCESS;

    }

    public StatusCode transferMoney(String fromAccountId, String toAccountId, int amount){
        Account fAccount = accountRepository.findById(fromAccountId).orElse(null);
        Account tAccount = accountRepository.findById(toAccountId).orElse(null);

        if(fAccount == null || tAccount == null){
            return StatusCode.BAD_REQUEST;
        }

        if(fAccount.getBalance() < amount){
            return StatusCode.NOT_ENOUGH_MONEY;
        }

        // 출금 및 입금 작업 수행
        fAccount.withdraw(amount);
        tAccount.deposit(amount);

        // 변경 사항 저장
        accountRepository.save(fAccount);
        accountRepository.save(tAccount);

        return StatusCode.SUCCESS;

    }
    public StatusCode registerAccount(Long userId){

        User sUser = userRepository.findById(userId).orElse(null);

        if(sUser == null) {
            return StatusCode.BAD_REQUEST;
        }

        String newAccountId = makeRandomAccount();

        while(accountRepository.existsById(newAccountId)){
            newAccountId = makeRandomAccount();
        }
        //중복 체크

        Account newAccount = Account.builder()
                .user(sUser)
                .accountId(newAccountId)
                .build();

        sUser.getAccounts().add(newAccount);
        //유저에 추가
        //accountRepository.save(newAccount);

        return StatusCode.SUCCESS;
    }


    





}
