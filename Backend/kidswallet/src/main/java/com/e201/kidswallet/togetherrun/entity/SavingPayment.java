package com.e201.kidswallet.togetherrun.entity;

import com.e201.kidswallet.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "saving_payment")
public class SavingPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saving_payment_id")
    private long savingPaymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saving_contract_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private SavingContract savingContract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;

    @Column(name = "deposit_date")
    private LocalDateTime depositDate;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
