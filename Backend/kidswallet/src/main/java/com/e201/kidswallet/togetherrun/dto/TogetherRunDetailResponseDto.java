package com.e201.kidswallet.togetherrun.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class TogetherRunDetailResponseDto {
    private String targetTitle;
    private String targetImage;
    private BigDecimal targetAmount;
    private LocalDate expiredAt;
    private int dDay;
    private BigDecimal childAmount;
    private BigDecimal parentAmount;
}