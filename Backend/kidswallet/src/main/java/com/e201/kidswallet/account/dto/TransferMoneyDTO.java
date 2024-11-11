package com.e201.kidswallet.account.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Getter
public class TransferMoneyDTO {
    private String fromId;
    private String toId;
    private String message;
    private int amount;

}
