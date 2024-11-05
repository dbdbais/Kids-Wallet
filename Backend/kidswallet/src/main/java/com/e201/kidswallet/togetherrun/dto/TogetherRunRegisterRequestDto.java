package com.e201.kidswallet.togetherrun.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class TogetherRunRegisterRequestDto {
    private Long userId;
    private Long partnerId;
    private MultipartFile targetImage;
    private BigDecimal targetCost;
    private short childRate;
    private short parentsRate;
    private short runningWeek;
}
