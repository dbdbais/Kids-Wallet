package com.e201.kidswallet.account;

import com.e201.kidswallet.AbstractTest;
import com.e201.kidswallet.account.dto.TransferMoneyDTO;
import com.e201.kidswallet.account.entity.Account;
import com.e201.kidswallet.account.service.AccountService;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountTest extends AbstractTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    AccountService accountService;
    @Autowired
    UserRepository userRepository;

    static long prentsId;
    static long childId;
    static long wrongId;
    static Account pAccount;
    static Account cAccount;

    @BeforeEach
    public void setUp() {
        prentsId = 1;
        childId = 2;
        wrongId = 0;
    }

    //계좌 생성 테스트
    @Test
    @Order(1)
    public void registerAccountTest() {
        StatusCode pStatus = accountService.registerAccount(prentsId);
        StatusCode cStatus = accountService.registerAccount(childId);
        StatusCode wStatus = accountService.registerAccount(wrongId);

        assertEquals(StatusCode.SUCCESS, pStatus);
        assertEquals(StatusCode.SUCCESS, cStatus);
        assertEquals(StatusCode.BAD_REQUEST, wStatus);
    }
    // 계좌조회
    @Test
    @Order(2)
    @Transactional
    public void getAccount(){
        // 계좌조회 service 대체
        List<Account> pAccounts = userRepository.findById(prentsId).get().getAccounts();
        List<Account> cAccounts =userRepository.findById(childId).get().getAccounts();

        Hibernate.initialize(pAccounts);
        Hibernate.initialize(cAccounts);

        pAccount = pAccounts.get(0);
        cAccount = cAccounts.get(0);

        System.out.println(pAccount.getAccountId());
        System.out.println(cAccount.getAccountId());


    }

    // 입금
    @Test
    @Order(3)
    public void depositTest() {

        StatusCode pStatus = accountService.depositMoney(pAccount.getAccountId(),100000000);
        StatusCode cStatus = accountService.depositMoney(cAccount.getAccountId(),1);

        assertEquals(StatusCode.SUCCESS,pStatus);
        assertEquals(StatusCode.SUCCESS,cStatus);
    }

    // 이체
    @Test
    @Order(4)
    public void transferMoneyTest() {
        TransferMoneyDTO inputDto = new TransferMoneyDTO(pAccount.getAccountId(),
                                                        cAccount.getAccountId(),
                                                        "필살..! 소매넣기!",
                                                        10000);
        assertEquals(StatusCode.SUCCESS,accountService.transferMoney(inputDto));

    }

//    // 트랜잭션 내역 조회
//    @Test
//    @Order(2)
//    public void transactionTest() {
//       TransactionListDTO pResult=accountService.getTransaction(pAccount.getAccountId());
//       System.out.println(pResult);
//    }

    // 잔액 조회






}
