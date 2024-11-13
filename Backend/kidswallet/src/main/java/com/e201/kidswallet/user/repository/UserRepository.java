package com.e201.kidswallet.user.repository;

import com.e201.kidswallet.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserRealName(String username);
    Boolean existsByUserName(String username);

}
