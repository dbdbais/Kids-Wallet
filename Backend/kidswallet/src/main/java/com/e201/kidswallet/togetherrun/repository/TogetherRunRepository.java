package com.e201.kidswallet.togetherrun.repository;

import com.e201.kidswallet.togetherrun.entity.TogetherRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TogetherRunRepository extends JpaRepository<TogetherRun, Long> {

    @Query(value = "SELECT sc.saving_contract_id, tr.target_title, sc.current_amount, " +
            "DATEDIFF(sc.expired_at, CURRENT_DATE), " +
            "CASE WHEN tr.status = 0 THEN false ELSE true END " +
            "FROM together_run tr " +
            "JOIN relation r ON tr.relation_id = r.relation_id " +
            "JOIN user child_user ON r.children_id = child_user.user_id " +
            "JOIN user parent_user ON r.parent_id = parent_user.user_id " +
            "JOIN saving_contract sc ON tr.saving_contract_id = sc.saving_contract_id " +
            "WHERE child_user.user_id = :userId OR parent_user.user_id = :userId",
            nativeQuery = true)
    List<Object[]> findTogetherRunInfoByUserId(@Param("userId") Long userId);

    @Query("SELECT tr FROM TogetherRun tr WHERE tr.savingContract.savingContractId = :savingContractId")
    Optional<TogetherRun> findBySavingContractId(@Param("savingContractId") Long savingContractId);

//    @Query("SELECT tr FROM TogetherRun tr WHERE tr.relation IN :relations")
//    List<TogetherRun> findByRelationIn(@Param("relations") List<Relation> relations);
//
//    @Query("SELECT new com.example.TogetherRunProjection(tc.savingContract.savingContractId, r.child.userRealName, r.parent.userRealName, tc.targetAmount, sc.currentAmount, sc.currentInterestAmount, sc.expiredAt) " +
//            "FROM TogetherRun tc " +
//            "JOIN tc.savingContract sc " +
//            "JOIN tc.relation r " +
//            "WHERE tc.togetherRunId = :togetherRunId")
//    List<TogetherRunProjection> findTogetherRunDetails(@Param("togetherRunId") Integer togetherRunId);
}

