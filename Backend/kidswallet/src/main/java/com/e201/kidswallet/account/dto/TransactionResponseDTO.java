package com.e201.kidswallet.account.dto;

import com.e201.kidswallet.transaction.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TransactionResponseDTO {
    private String accountId;
    private String message;
    private TransactionType transactionType;
    private int amount;
    private LocalDateTime transactionDate;
}
