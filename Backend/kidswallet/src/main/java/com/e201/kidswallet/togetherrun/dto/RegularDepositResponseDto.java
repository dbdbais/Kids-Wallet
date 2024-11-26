package com.e201.kidswallet.togetherrun.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class RegularDepositResponseDto {
    private int amount;
    private short depositDay;
    private LocalDate startDate;
    private LocalDate endDate;
}
