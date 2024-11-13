package com.e201.kidswallet.mission.repository;


import org.springframework.data.repository.query.Param;
import com.e201.kidswallet.mission.entity.Beg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BegRepository extends JpaRepository<Beg,Long> {
    @Modifying
    @Query(value="update beg set beg_accept = :isAccept where beg_id = :begId;",nativeQuery = true)
    public int updateAccept(@Param("isAccept") boolean isAccept, @Param("begId") long begId );
}
