package com.e201.kidswallet.togetherrun.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
public class TogetherRunDetailResponseDto {
    private Long savingContractId;
    private String targetTitle;
    private String targetImage;
    private int targetAmount;
    private LocalDate expiredAt;
    @JsonProperty("dDay")
    private int dDay;
    @JsonProperty("isAccept")
    private Boolean isAccept;
    private int childAmount;
    private int childGoalAmount;
    private String childName;
    private int parentAmount;
    private int parentGoalAmount;
    private String parentName;
}