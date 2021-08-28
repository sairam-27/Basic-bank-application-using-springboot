package com.spring.project.bankapp.service;

import org.springframework.data.jpa.repository.Query;

import com.spring.project.bankapp.models.Account;

public interface AccountService {
	
	public void saveAccount(Account account);
	
	public Account getAccountByAccountNumber(int accNum);

}
