package com.e201.kidswallet.together.repository;

import com.e201.kidswallet.together.entity.TogetherRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TogetherRepository extends JpaRepository<TogetherRun,Long> {
}
