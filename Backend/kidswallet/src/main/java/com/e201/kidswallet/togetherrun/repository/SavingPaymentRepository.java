package com.e201.kidswallet.togetherrun.repository;

import com.e201.kidswallet.togetherrun.entity.SavingPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SavingPaymentRepository extends JpaRepository<SavingPayment, Long> {
    @Query("SELECT SUM(sp.depositAmount) FROM SavingPayment sp WHERE sp.savingContract.savingContractId = :savingContractId AND sp.user.userId = :userId")
    BigDecimal findTotalDepositAmountBySavingContractIdAndUserId(@Param("savingContractId") Long savingContractId, @Param("userId") Long userId);

}
