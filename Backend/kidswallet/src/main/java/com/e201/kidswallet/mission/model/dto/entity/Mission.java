package com.e201.kidswallet.mission.model.dto.entity;

import com.e201.kidswallet.beg.model.dto.entity.Beg;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="mission_id",nullable = false)
    private Long MissionId;

    @Enumerated(value=EnumType.STRING)
    @Column(name="mission_status",nullable = false)
    private Status missionStatus;

    @Column(name="completion_photo",nullable = true)
    private String completionPhoto;

    @Column(name="completed_at",nullable = true)
    private LocalDateTime completedAt;

    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "beg_id", referencedColumnName = "beg_id")
    private Beg beg;


}
