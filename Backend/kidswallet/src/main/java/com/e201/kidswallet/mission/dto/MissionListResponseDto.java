package com.e201.kidswallet.mission.dto;

import com.e201.kidswallet.mission.entity.Beg;
import com.e201.kidswallet.mission.entity.Mission;
import com.e201.kidswallet.mission.enums.Status;
import com.e201.kidswallet.user.entity.Relation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissionListResponseDto {
    String name;
    BegDto begDto;
    MissionDto mission;

}
