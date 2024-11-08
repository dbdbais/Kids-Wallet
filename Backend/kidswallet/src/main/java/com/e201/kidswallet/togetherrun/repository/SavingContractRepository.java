package com.e201.kidswallet.togetherrun.repository;

import com.e201.kidswallet.togetherrun.entity.SavingContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SavingContractRepository extends JpaRepository<SavingContract, Long>{

    @Query("SELECT sc FROM SavingContract sc WHERE (sc.depositDay % 7 + 1) = DAYOFWEEK(CURRENT_DATE)")
    List<SavingContract> findDepositContractForToday();
}
