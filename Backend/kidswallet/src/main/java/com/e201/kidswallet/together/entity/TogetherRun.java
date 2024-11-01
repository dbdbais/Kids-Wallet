package com.e201.kidswallet.together.entity;

import com.e201.kidswallet.user.model.dto.entity.Relation;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class TogetherRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long togetherRunId;

    @OneToOne()
    @JoinColumn(name = "saving_contract_id")
    private SavingContract savingContract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relation_id")
    private Relation relation;

    private LocalDate createdAt;


}
