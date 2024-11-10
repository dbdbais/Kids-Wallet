package com.e201.kidswallet.togetherrun.entity;

import com.e201.kidswallet.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    @Builder.Default()
    @Column(name="current_amount")
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Builder.Default()
    @Column(name="current_interest_amount")
    private BigDecimal currentInterestAmount = BigDecimal.ZERO;

    @Builder.Default()
    @Column(name="status")
    private SavingContractStatus status = SavingContractStatus.PROCEED;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="expired_at")
    private LocalDate expiredAt;

    @Column(name="saving_account")
    private String savingAccount;

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "savingContract", cascade = CascadeType.ALL)
    private List<SavingPayment> savingPayments = new ArrayList<>();

}


