package com.e201.kidswallet.mission.repository;

import com.e201.kidswallet.mission.entity.Mission;
import com.e201.kidswallet.mission.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MissionRepository extends JpaRepository<Mission,Long> {

    @Modifying
    @Query(value="update mission set completion_photo = :uploadCompleteImage , mission_status = :status where mission_id= :missionId",nativeQuery = true)
    public int uploadCompleteImage(@Param("uploadCompleteImage") byte[] uploadCompleteImage, @Param("missionId") Long missionId, @Param("status") String status);

//    @Modifying
//    @Query(value = "UPDATE mission SET mission_status = :status, completed_at = :updateTime WHERE mission_id = :missionId",nativeQuery = true)
//    public int updateMissionStatus(@Param("missionId") long missionId, @Param("status") String status, @Param("updateTime") LocalDateTime updateTime);
}
