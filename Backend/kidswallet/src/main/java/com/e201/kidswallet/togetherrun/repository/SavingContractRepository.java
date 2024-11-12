package com.e201.kidswallet.togetherrun.repository;

import com.e201.kidswallet.togetherrun.entity.SavingContract;
import com.e201.kidswallet.togetherrun.entity.enums.SavingContractStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SavingContractRepository extends JpaRepository<SavingContract, Long>{

    @Query("SELECT sc FROM SavingContract sc JOIN FETCH sc.user JOIN FETCH sc.saving WHERE (MOD(sc.depositDay, 7) + 1) = DAYOFWEEK(CURRENT_DATE) AND sc.status = 0")
    List<SavingContract> findDepositContractForToday(Pageable pageable);

    @Query("SELECT sc FROM SavingContract sc WHERE sc.status = 0")
    List<SavingContract> findByStatus(Pageable pageable);
}
