package com.e201.kidswallet.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TransferMoneyDTO {
    String fAccountId;
    String tAcoountId;
    String message;
    int amount;

}
