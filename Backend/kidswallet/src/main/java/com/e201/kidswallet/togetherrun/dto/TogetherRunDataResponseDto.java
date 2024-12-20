package com.e201.kidswallet.togetherrun.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@Setter
public class TogetherRunDataResponseDto {
    private Long togetherRunId;
    private String targetTitle;
    private int targetAmount;
    @JsonProperty("dDay")
    private Long dDay;
    @JsonProperty("isAccept")
    private Boolean isAccept;
}
