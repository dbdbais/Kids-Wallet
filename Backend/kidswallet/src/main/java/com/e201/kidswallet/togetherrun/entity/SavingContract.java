package com.e201.kidswallet.togetherrun.entity;

import com.e201.kidswallet.user.model.dto.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class SavingContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="saving_contract_id")
    private Long savingContractId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="saving_id", nullable = false)
    private Saving saving;

    @OneToMany(mappedBy = "savingContract")
    private List<SavingPayment> savingPayments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name="parents_account")
    private String parentsAccount;

    @Column(name="child_account")
    private String childAccount;

    @Column(name="deposit_day")
    private short depositDay;

    @Column(name="target_amount")
    private BigDecimal targetAmount;

    @Column(name="status")
    private ContractStatus status;

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="expired_at")
    private LocalDateTime expiredAt;

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;
}

enum ContractStatus {
    PROCEED,
    COMPLETED
}
