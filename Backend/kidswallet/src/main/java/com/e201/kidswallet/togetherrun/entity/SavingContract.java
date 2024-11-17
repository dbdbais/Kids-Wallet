package com.e201.kidswallet.togetherrun.entity;

import com.e201.kidswallet.togetherrun.entity.enums.SavingContractPaymentCheck;
import com.e201.kidswallet.togetherrun.entity.enums.SavingContractStatus;
import com.e201.kidswallet.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
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
    private Long savingContractId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="saving_id", nullable = false)
    private Saving saving;

    @Column(name = "saving_account")
    private String savingAccount;

    @Column(name = "deposit_day")
    private short depositDay;

    @Column(name = "child_deposit_amount")
    private BigDecimal childDepositAmount;

    @Column(name = "parents_deposit_amount")
    private BigDecimal parentsDepositAmount;

    @Builder.Default()
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "payment_check")
    private SavingContractPaymentCheck paymentCheck = SavingContractPaymentCheck.PAYMENT;

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

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "savingContract", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<SavingPayment> savingPayments = new ArrayList<>();

}