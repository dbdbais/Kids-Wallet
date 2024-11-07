package com.e201.kidswallet.togetherrun.entity;

import com.e201.kidswallet.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EnableJpaAuditing
public class SavingContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="saving_contract_id")
    private long savingContractId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="saving_id", nullable = false)
    private Saving saving;

    @Column(name = "deposit_day")
    private short depositDay;

    @Column(name="current_amount")
    private BigDecimal currentAmount;

    @Column(name="current_interest_amount")
    private BigDecimal currentInterestAmount;

    @Column(name="status")
    private SavingContractStatus status = SavingContractStatus.PROCEED;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="expired_at")
    private LocalDateTime expiredAt;

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "savingContract", cascade = CascadeType.ALL)
    private List<SavingPayment> savingPayments = new ArrayList<>();
}


