package com.e201.kidswallet.togetherrun.entity;

import com.e201.kidswallet.user.entity.Relation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TogetherRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long togetherRunId;

    @OneToOne()
    @JoinColumn(name = "saving_contract_id")
    private SavingContract savingContract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relation_id", referencedColumnName = "relation_id")
    private Relation relation;

    @Column(name = "target_image")
    private String targetImage;

    @Column(name = "parents_account")
    private String parentsAccount;

    @Column(name = "parents_contribute")
    private BigDecimal parentsContribute;

    @Column(name = "child_account")
    private String childAccount;

    @Column(name = "child_contribute")
    private BigDecimal childContribute;

    @Column(name = "target_amount")
    private BigDecimal targetAmount;

    @Column(name = "target_date")
    private String targetDate;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private TogetherRunStatus status = TogetherRunStatus.PENDING;
}
