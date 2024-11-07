package com.e201.kidswallet.transaction.repository;

import com.e201.kidswallet.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
