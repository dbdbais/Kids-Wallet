package com.e201.kidswallet.together.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class SavingPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saving_payment_id")
    private Long savingPaymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saving_contract_id", nullable = false)
    private SavingContract savingContract;

    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;

    @Column(name = "deposit_date")
    private LocalDate depositDate;

    @Column(name = "created_at")
    private LocalDate createdAt;

}
