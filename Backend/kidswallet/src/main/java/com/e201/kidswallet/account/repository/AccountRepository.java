package com.e201.kidswallet.account.repository;

import com.e201.kidswallet.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {

}
