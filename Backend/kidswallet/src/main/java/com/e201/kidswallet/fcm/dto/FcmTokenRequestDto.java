package com.e201.kidswallet.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FcmTokenRequestDto {
    private long userId;
    private String tokenValue;
    private LocalDateTime createAt;
}
