package com.spring.project.bankapp.Exceptions;

import org.springframework.stereotype.Component;

import com.spring.project.bankapp.models.Account;

@Component
public class AccountsErrors{
	
	public Account InsufficientFunds(Account acc) {
		return acc;
	}
	
	public Account MaxCreditAmountReached(Account acc) {
		return acc;
	}
	
}
