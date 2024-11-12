package com.e201.kidswallet.togetherrun.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class TogetherRunDataResponseDto {
    private long savingContractId;
    private String targetTitle;
    private BigDecimal currentAmount;
    private int dDay;
}
