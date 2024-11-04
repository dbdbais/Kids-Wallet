package com.e201.kidswallet.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissionCompleteCheckRequestDto {

    private long missionId;
    private Boolean isComplete;
}
