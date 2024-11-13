package com.e201.kidswallet.mission.dto;

import com.e201.kidswallet.mission.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissionDto {
    private Long MissionId;
    private Status missionStatus;
    private byte[] completionPhoto;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
    private String missionContent;
}
