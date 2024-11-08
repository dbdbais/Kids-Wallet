package com.e201.kidswallet.togetherrun.entity;

import com.e201.kidswallet.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Entity
@Data
@EnableJpaAuditing
public class SavingPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saving_payment_id")
    private long savingPaymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saving_contract_id", nullable = false)
    private SavingContract savingContract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;

    @Column(name = "deposit_date")
    private LocalDateTime depositDate;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}
