package com.e201.kidswallet.mission.dto;

import com.e201.kidswallet.mission.entity.Beg;
import com.e201.kidswallet.mission.entity.Mission;
import com.e201.kidswallet.mission.enums.Status;
import com.e201.kidswallet.user.entity.Relation;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MissionListResponseDto {
    String name;
    BegDto begDto;
    MissionDto mission;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissionListResponseDto that = (MissionListResponseDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(begDto, that.begDto) &&
                Objects.equals(mission, that.mission);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, begDto, mission);
    }
}
