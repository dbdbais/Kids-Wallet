package com.e201.kidswallet.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class TransferMoneyDTO {
    String fAccountId;
    String tAccountId;
    String message;
    int amount;

}
