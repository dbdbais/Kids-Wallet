package com.e201.kidswallet.account.controller;

import com.e201.kidswallet.account.dto.AccountMoneyDTO;
import com.e201.kidswallet.account.service.AccountService;
import com.e201.kidswallet.common.ResponseDto;
import com.e201.kidswallet.common.exception.StatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/regist/{userId}")
    public ResponseEntity<ResponseDto> createAccount(@PathVariable Long userId){
        StatusCode returnCode = accountService.registerAccount(userId);
        return ResponseDto.response(returnCode);
    }

    @PatchMapping("/deposit")
    public ResponseEntity<ResponseDto> depositAccount(@RequestBody AccountMoneyDTO accountDepositDTO){
        StatusCode returnCode = accountService.depositMoney(accountDepositDTO.getAccountId(),accountDepositDTO.getAmount());
        return ResponseDto.response(returnCode);
    }

    @PatchMapping("/withdraw")
    public ResponseEntity<ResponseDto> withdrawAccount(@RequestBody AccountMoneyDTO accountwithdrawDTO){
        StatusCode returnCode = accountService.withdrawMoney(accountwithdrawDTO.getAccountId(),accountwithdrawDTO.getAmount());
        return ResponseDto.response(returnCode);
    }


}
