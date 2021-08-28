package com.spring.project.bankapp.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.spring.project.bankapp.models.Account;
import com.spring.project.bankapp.repository.AccountsRepository;

@Service
public class AccountServiceImpl implements AccountService{
	
	AccountsRepository accountRepository;
	
	public AccountServiceImpl(AccountsRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	@Transactional
	public void saveAccount(Account account) {
		accountRepository.save(account);
	}

	@Override
	public Account getAccountByAccountNumber(int accNum) {
		return accountRepository.findByAccNum(accNum);
	}

}
