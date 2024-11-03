package com.e201.kidswallet.mission.entity;

import com.e201.kidswallet.mission.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Mission")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="mission_id",nullable = false)
    private Long MissionId;

    @Builder.Default
    @Enumerated(value=EnumType.STRING)
    @Column(name="mission_status",nullable = false)
    private Status missionStatus=Status.request;

    @Column(name="completion_photo",nullable = true)
    private String completionPhoto;

    @Column(name="completed_at",nullable = true)
    private LocalDateTime completedAt;

    @CreatedDate
    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name="mission_content",nullable = false)
    private String missionContent;

    @Column(name="dead_line",nullable = false)
    private LocalDateTime deadLine;

    @OneToOne
    @JoinColumn(name = "beg_id", referencedColumnName = "beg_id")
    private Beg beg;
}
