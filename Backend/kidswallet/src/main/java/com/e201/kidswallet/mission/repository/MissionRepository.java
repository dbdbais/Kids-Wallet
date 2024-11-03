package com.e201.kidswallet.mission.repository;

import com.e201.kidswallet.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission,Long> {
}
