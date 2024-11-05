package com.e201.kidswallet.togetherrun.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TogetherRunDataResponseDto {
    private Long savingContractId;
    private Long childId;
    private Long parentsId;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private BigDecimal currentInterestAmount;
    private LocalDateTime expiredAt;
}
