package com.spring.project.bankapp.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectExpressions {
	
	@Pointcut("execution(* com.spring.project.bankapp.controllers.AccountsController.*Amount(..))")
	public void aspectForCreditAndDebitController() {};
	
	@Pointcut("execution(* com.spring.project.bankapp.Exceptions.AccountsErrors.*(..))")
	public void aspectForAccountErrors() {};
	
	@Pointcut("execution(* com.spring.project.bankapp.Exceptions.AccountsErrors.insufficientFunds(..))")
	public void aspectForinsufficientFunds() {};
	
	@Pointcut("execution(* com.spring.project.bankapp.Exceptions.AccountsErrors.maxCreditAmountReached(..))")
	public void aspectForMaxCredit() {};
	
	@Pointcut("execution(* com.spring.project.bankapp.service.*ServiceImpl.saveAccount(..))")
	public void aspectForSaveAccount() {};
	
	@Pointcut("execution(* com.spring.project.bankapp.service.AccountServiceImpl.getAccountByAccountNumber(..))")
	public void aspectForGetAccount() {};


}