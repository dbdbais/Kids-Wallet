package com.e201.kidswallet.togetherrun.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TogetherRunDetailResponseDto {
    private Long userRealname;
    private BigDecimal currentAmount;
    private BigDecimal depositAmount;
    private LocalDateTime depositedAt;
}
