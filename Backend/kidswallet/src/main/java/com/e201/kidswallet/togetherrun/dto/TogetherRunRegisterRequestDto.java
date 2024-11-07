package com.e201.kidswallet.togetherrun.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TogetherRunRegisterRequestDto {
    private long parentsId;
    private long childId;
    private MultipartFile targetImage;
    private BigDecimal targetAmount;
    private String targetDate;
    private BigDecimal parentsContribute;
    private BigDecimal childContribute;
}
