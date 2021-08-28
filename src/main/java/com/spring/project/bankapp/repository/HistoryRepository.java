package com.spring.project.bankapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.project.bankapp.models.Account;
import com.spring.project.bankapp.models.History;

public interface HistoryRepository extends JpaRepository<History, Integer>{

	 @Query(value="select *from history where account_holder_name = ?1 order by action_date DESC ", nativeQuery=true)
	 public List<History> findRecordsByUsername(String empName);
	
}
