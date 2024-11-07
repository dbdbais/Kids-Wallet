package com.e201.kidswallet.togetherrun.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class TogetherRunCancelResponseDto {
    private BigDecimal cancelAmount;
}
