package com.e201.kidswallet.beg.entity;

import com.e201.kidswallet.mission.entity.Mission;
import com.e201.kidswallet.user.entity.Relation;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Beg")
public class Beg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="beg_id",nullable = false)
    private Long begId;

    @Column(name="beg_money",nullable=false)
    private int begMoney= 0;

    @Column(name="beg_content",nullable = false)
    private String begContent;

    @CreatedDate
    @Column(name="create_at",nullable = false)
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name="relation_id",referencedColumnName = "relation_id")
    private Relation relation;

    @OneToOne(mappedBy = "beg")
    private Mission mission;
}