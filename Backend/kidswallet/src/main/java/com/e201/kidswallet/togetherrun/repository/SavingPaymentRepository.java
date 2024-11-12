package com.e201.kidswallet.togetherrun.repository;

import com.e201.kidswallet.togetherrun.entity.SavingPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingPaymentRepository extends JpaRepository<SavingPayment, Long> {
}
