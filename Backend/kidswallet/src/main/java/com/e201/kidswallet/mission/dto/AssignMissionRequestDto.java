package com.e201.kidswallet.mission.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssignMissionRequestDto {

    private long begId;
    private String missionMessage;
    private LocalDateTime deadline;

}
