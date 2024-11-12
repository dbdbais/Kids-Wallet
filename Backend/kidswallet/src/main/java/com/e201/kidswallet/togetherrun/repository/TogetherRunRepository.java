package com.e201.kidswallet.togetherrun.repository;

import com.e201.kidswallet.togetherrun.dto.TogetherRunDataResponseDto;
import com.e201.kidswallet.togetherrun.entity.TogetherRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TogetherRunRepository extends JpaRepository<TogetherRun, Long> {

    @Query("SELECT new com.e201.kidswallet.togetherrun.dto.TogetherRunDataResponseDto(" +
            "sc.savingContractId, " +
            "tr.targetTitle, " +
            "sc.currentAmount, " +
            "DATEDIFF(sc.expiredAt, CURRENT_DATE)) " +
            "FROM TogetherRun tr " +
            "JOIN tr.relation r " +
            "JOIN r.child childUser " +
            "JOIN r.parent parentUser " +
            "JOIN tr.savingContract sc " +
            "WHERE childUser.userId = :userId OR parentUser.userId = :userId")
    List<TogetherRunDataResponseDto> findTogetherRunInfoByUserId(@Param("userId") Long userId);

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

