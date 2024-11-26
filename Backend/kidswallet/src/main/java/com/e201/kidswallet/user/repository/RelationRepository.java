package com.e201.kidswallet.user.repository;

import com.e201.kidswallet.user.entity.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RelationRepository extends JpaRepository<Relation,Long> {
    @Query("SELECT distinct r FROM Relation r JOIN fetch r.parent p JOIN fetch r.child c WHERE p.userId = :userId OR c.userId = :userId")
    List<Relation> findRelation(@Param("userId") Long userId);
}
