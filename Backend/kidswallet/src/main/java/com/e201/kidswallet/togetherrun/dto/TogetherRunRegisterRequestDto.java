package com.e201.kidswallet.togetherrun.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TogetherRunRegisterRequestDto {
    private long parentsId;
    private long childId;
    private String targetTitle;
    private BigDecimal targetAmount;
    private LocalDate targetDate;
    private BigDecimal parentsContribute;
    private BigDecimal childContribute;
}
