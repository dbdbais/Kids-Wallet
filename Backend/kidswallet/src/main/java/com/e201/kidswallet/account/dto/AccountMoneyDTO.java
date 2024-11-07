package com.e201.kidswallet.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class AccountMoneyDTO {
    String accountId;
    int amount;
}
