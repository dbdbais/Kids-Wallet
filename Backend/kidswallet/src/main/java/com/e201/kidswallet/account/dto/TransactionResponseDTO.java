package com.e201.kidswallet.account.dto;

import com.e201.kidswallet.transaction.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class TransactionResponseDTO {
    String accountId;
    String message;
    TransactionType transactionType;
    int amount;
    LocalDateTime transactionDate;
}
