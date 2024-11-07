package com.e201.kidswallet.togetherrun.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class TogetherRunDataResponseDto {
    private long savingContractId;
    private String childRealName;
    private String parentsRealName;
    private String targetTitle;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private BigDecimal currentInterestAmount;
    private LocalDateTime expiredAt;
}
