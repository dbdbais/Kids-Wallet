package com.e201.kidswallet.togetherrun.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class TogetherRunDetailResponseDto {
    private String userRealName;
    private BigDecimal currentAmount;
    private BigDecimal depositAmount;
    private LocalDateTime depositedAt;
}
