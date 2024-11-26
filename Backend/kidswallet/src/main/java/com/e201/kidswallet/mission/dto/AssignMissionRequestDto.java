package com.e201.kidswallet.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignMissionRequestDto {
    private long begId;
    private String missionMessage;
}
