package com.e201.kidswallet.mission.dto;

import com.e201.kidswallet.mission.enums.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MissionDto {
    private Long MissionId;
    private Status missionStatus;
    private byte[] completionPhoto;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
    private String missionContent;
    private LocalDateTime deadLine;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissionDto that = (MissionDto) o;
        return Objects.equals(MissionId, that.MissionId) &&
                missionStatus == that.missionStatus &&
                Objects.deepEquals(completionPhoto, that.completionPhoto) &&
                Objects.equals(missionContent, that.missionContent);
    }
    @Override
    public int hashCode() {
        return Objects.hash(MissionId, missionStatus, Arrays.hashCode(completionPhoto), missionContent);
    }
}
