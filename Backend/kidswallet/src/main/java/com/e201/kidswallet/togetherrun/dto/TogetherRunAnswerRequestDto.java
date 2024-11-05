package com.e201.kidswallet.togetherrun.dto;

import lombok.Data;

@Data
public class TogetherRunAnswerRequestDto {
    private Long userId;
    private Long togetherRunId;
    private boolean isAccept;
}
