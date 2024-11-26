package com.e201.kidswallet.togetherrun.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EnableJpaAuditing
@Table(name = "saving")
public class Saving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saving_id")
    private long savingId;

    @Column(name = "saving_name")
    private String savingName;

    @Column(name = "min_amount")
    private BigDecimal minAmount;

    @Column(name = "max_amount")
    private BigDecimal maxAmount;

    @Column(name = "min_period_week")
    private short minPeriodWeek;

    @Column(name = "max_period_week")
    private short maxPeriodWeek;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.ORDINAL)
    private SavingStatus status;
}

enum SavingStatus {
    ACTIVE,
    INACTIVE,
    DISCONTINUED
}
