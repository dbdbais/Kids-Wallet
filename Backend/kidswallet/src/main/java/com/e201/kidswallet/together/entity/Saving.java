package com.e201.kidswallet.together.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Saving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saving_id")
    private Long savingId;

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
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.ORDINAL)
    private SavingStatus status;
}

enum SavingStatus {
    ACTIVE,
    INACTIVE,
    DISCONTINUED
}
