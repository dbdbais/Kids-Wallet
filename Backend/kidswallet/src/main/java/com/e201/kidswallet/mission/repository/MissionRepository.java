package com.e201.kidswallet.mission.repository;

import com.e201.kidswallet.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission,Long> {

    @Modifying
    @Query(value="update Mission set completion_photo = :uploadCompleteImage where mission_id= :missionId",nativeQuery = true)
    public int uploadCompleteImage(@Param("uploadCompleteImage") byte[] uploadCompleteImage, @Param("missionId") Long missionId);


}
