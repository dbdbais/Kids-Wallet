package com.e201.kidswallet.account.entity;

import com.e201.kidswallet.transaction.entity.Transaction;
import com.e201.kidswallet.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name="account")
public class Account {
    @Id
    @Column(name="account_id",nullable = false,unique = true)
    private String accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder.Default
    @Column(name = "balance", nullable = false)
    private int balance = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany
    @JoinTable(
            name = "account_transaction",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id")
    )
    private List<Transaction> transactions = new ArrayList<>();

    // 거래를 위한 입금
    public void deposit(int amount) {
        this.balance += amount;
    }

    // 거래를 위한 출금
    public void withdraw(int amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
        } else {
            throw new IllegalArgumentException("잔액 부족");
        }
    }
    // 계좌에 Transaction 더한다.
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

}
