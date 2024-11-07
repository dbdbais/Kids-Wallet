package com.e201.kidswallet.mission.entity;

import com.e201.kidswallet.mission.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Mission")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="mission_id",nullable = false)
    private Long MissionId;

    @Builder.Default
    @Enumerated(value=EnumType.STRING)
    @Column(name="mission_status",nullable = false)
    private Status missionStatus=Status.PROCEED;

    @Column(name="completion_photo",nullable = true,columnDefinition = "LONGBLOB")
    @Lob // 큰 데이터 저장을 위한 어노테이션
    private byte[] completionPhoto;

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


    public void changeMissionStatusAndCompleteTime(Status status) {
        this.completedAt = LocalDateTime.now();
        this.missionStatus = status;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "MissionId=" + MissionId +
                ", missionStatus=" + missionStatus +
                ", completionPhoto=" + Arrays.toString(completionPhoto) +
                ", completedAt=" + completedAt +
                ", createdAt=" + createdAt +
                ", missionContent='" + missionContent + '\'' +
                ", deadLine=" + deadLine +
                ", beg=" + beg +
                '}';
    }
}
