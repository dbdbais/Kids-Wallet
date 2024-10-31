package com.e201.kidswallet.beg.model.dto.entity;

import com.e201.kidswallet.mission.model.dto.entity.Mission;
import com.e201.kidswallet.user.model.dto.entity.Relation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name="create_at",nullable = false)
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name="relation_id",referencedColumnName = "relation_id")
    private Relation relation;

    @OneToMany(mappedBy = "beg")
    private List<Mission> missions;
}