package com.e201.kidswallet.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class AccountInfoResponseDTO {
    private String accountId;
    private int balance;
    private LocalDateTime createdAt;
}
