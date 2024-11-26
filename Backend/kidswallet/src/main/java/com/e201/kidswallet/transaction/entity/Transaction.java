package com.e201.kidswallet.transaction.entity;

import com.e201.kidswallet.account.entity.Account;
import com.e201.kidswallet.transaction.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id",nullable = false)
    private Long transactionId;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name="transaction_message",length = 15)
    private String message;

    // Transaction Type
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    // Transaction amount 작성
    @Column(name = "amount", nullable = false)
    private int amount;

    // Transaction 현재 잔액
    @Column(name= "current_balance")
    private int curAmount;



    // Transaction TimeStamp
    @CreatedDate
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;


    // 적금인지 확인
    public boolean isSavings() {
        return this.transactionType == TransactionType.SAVINGS;
    }


}
