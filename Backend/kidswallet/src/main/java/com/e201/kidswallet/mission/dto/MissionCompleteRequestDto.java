package com.e201.kidswallet.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissionCompleteRequestDto {

    private long missionId;
    private String base64Image; // Base64 인코딩된 이미지 문자열
}
