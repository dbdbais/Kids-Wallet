package com.e201.kidswallet.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BegDto {
    private Long begId;
    private int begMoney;
    private String begContent;
    private LocalDateTime createAt;
    private Boolean begAccept;
}
