package com.e201.kidswallet.togetherrun.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
public class TogetherRunDetailResponseDto {
    private String targetTitle;
    private String targetImage;
    private BigDecimal targetAmount;
    private LocalDate expiredAt;
    @JsonProperty("dDay")
    private int dDay;
    @JsonProperty("isAccept")
    private Boolean isAccept;
    private BigDecimal childAmount;
    private BigDecimal childGoalAmount;
    private String childName;
    private BigDecimal parentAmount;
    private BigDecimal parentGoalAmount;
    private String parentName;
}