package com.e201.kidswallet.beg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeggingRequestDto {

    private long userId;
    private String beggingMessage;
    private int beggingMoney;

}
