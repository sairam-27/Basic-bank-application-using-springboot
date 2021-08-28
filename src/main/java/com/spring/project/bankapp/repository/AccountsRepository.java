package com.spring.project.bankapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.project.bankapp.models.Account;

public interface AccountsRepository extends JpaRepository<Account, Integer>{
	
	 @Query(value="select *from account where account_number = ?1 limit 1", nativeQuery=true)
	 public Account findByAccNum(Integer empName);
	
}
