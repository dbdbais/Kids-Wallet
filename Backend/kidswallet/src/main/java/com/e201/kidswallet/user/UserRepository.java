package com.e201.kidswallet.user;

import com.e201.kidswallet.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
