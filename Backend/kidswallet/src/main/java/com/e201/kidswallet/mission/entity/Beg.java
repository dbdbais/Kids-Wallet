package com.e201.kidswallet.mission.entity;

import com.e201.kidswallet.user.entity.Relation;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name="beg")
public class Beg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="beg_id",nullable = false)
    private Long begId;

    @Builder.Default
    @Column(name="beg_money",nullable=false)
    private int begMoney=0;

    @Column(name="beg_content",nullable = false)
    private String begContent;

    @CreatedDate
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @Builder.Default
    @Column(name="beg_accept",nullable = true)
    private Boolean begAccept=null;

    @ManyToOne
    @JoinColumn(name="relation_id",referencedColumnName = "relation_id")
    private Relation relation;

    @OneToOne(mappedBy = "beg")
    private Mission mission;
}
