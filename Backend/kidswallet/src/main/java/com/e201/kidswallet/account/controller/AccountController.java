package com.e201.kidswallet.account.controller;

import com.e201.kidswallet.account.dto.AccountMoneyDTO;
import com.e201.kidswallet.account.dto.MonthlyExpenseDTO;
import com.e201.kidswallet.account.dto.TransactionListDTO;
import com.e201.kidswallet.account.dto.TransferMoneyDTO;
import com.e201.kidswallet.account.service.AccountService;
import com.e201.kidswallet.common.ResponseDto;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.fcm.service.FcmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;
    private final FcmService fcmService;

    public AccountController(AccountService accountService, FcmService fcmService) {
        this.accountService = accountService;
        this.fcmService = fcmService;
    }

    /**
     * 해당하는 유저의 계좌를 생성한다.
     * @param userId
     * @return
     */

    @PostMapping("/regist/{userId}")
    public ResponseEntity<ResponseDto> createAccount(@PathVariable Long userId){
        StatusCode returnCode = accountService.registerAccount(userId);
        return ResponseDto.response(returnCode);
    }

    /**
     * 해당하는 계좌에 amount만큼 잔액을 입금한다.
     * @param accountDepositDTO
     * @return
     */

    @PatchMapping("/deposit")
    public ResponseEntity<ResponseDto> depositAccount(@RequestBody AccountMoneyDTO accountDepositDTO){
        StatusCode returnCode = accountService.depositMoney(accountDepositDTO.getAccountId(),accountDepositDTO.getAmount());
        return ResponseDto.response(returnCode);
    }

    /**
     * 해당하는 계좌에 amount만큼 잔액을 출금한다.
     * @param accountWithdrawDTO
     * @return
     */

    @PatchMapping("/withdraw")
    public ResponseEntity<ResponseDto> withdrawAccount(@RequestBody AccountMoneyDTO accountWithdrawDTO){
        StatusCode returnCode = accountService.withdrawMoney(accountWithdrawDTO.getAccountId(),accountWithdrawDTO.getAmount());
        return ResponseDto.response(returnCode);
    }

    /**
     * fAccount -> tAccount로 이체한다.
     * @param transferMoneyDTO
     * @return
     */

    @PatchMapping("/transfer")
    public ResponseEntity<ResponseDto> transferAccount(@RequestBody TransferMoneyDTO transferMoneyDTO){
        StatusCode returnCode = accountService.transferMoney(transferMoneyDTO);
        if(returnCode == StatusCode.SUCCESS){

            if(fcmService.sendMessage(fcmService.getToken(accountService.getUserId(transferMoneyDTO.getToId())),"송금 봉투 도착","송금 봉투가 도착했습니다.") == StatusCode.TOKEN_IS_NULL){
                returnCode = StatusCode.TOKEN_IS_NULL;
            }
        }
        return ResponseDto.response(returnCode);
    }

    /**
     * 계좌의 모든 트랜잭션 내역을 조회한다.
     * @param id
     * @return
     */

    @GetMapping("/view/transaction")
    public ResponseEntity<ResponseDto> viewTransaction(@RequestParam String id){
       TransactionListDTO transactionListDTO = accountService.getTransaction(id);
       return ResponseDto.response(transactionListDTO.getStatusCode(),transactionListDTO.getLst());
    }

    /**
     * 이번 주와 이전 주의 소비내역을 7개의 배열에 넣어서 리턴
     * @param accountId
     * @return
     */

    @GetMapping("/weekly")
    public ResponseEntity<ResponseDto> test(@RequestParam String accountId){
        MonthlyExpenseDTO monthlyExpenseDTO = accountService.getMonthlyExpense(accountId);
        if(monthlyExpenseDTO != null){
            return ResponseDto.response(StatusCode.SUCCESS,monthlyExpenseDTO);
        }
        else{
            return ResponseDto.response(StatusCode.BAD_REQUEST);
        }
    }

}
