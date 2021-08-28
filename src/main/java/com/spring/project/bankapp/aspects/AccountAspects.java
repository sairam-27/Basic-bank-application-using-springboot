package com.spring.project.bankapp.aspects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.project.bankapp.models.Account;
import com.spring.project.bankapp.models.History;
import com.spring.project.bankapp.service.HistoryService;

@Aspect
@Component
public class AccountAspects {
	
	@Autowired
	HistoryService historyService;
	
	History historyRecord;
	String loginUser = null;
	String accountHolderName = null;
	long accountNumber = -1;
	String description = null;
	int balance = -1;
	int beforeBalance = -1;
	int afterbalance = -1;
	int amount = -1;
	String action = null;
	String date = null;
	
	@Before("com.spring.project.bankapp.aspects.AspectExpressions.aspectForCreditAndDebitController()")
	public void afterUserInitiating(JoinPoint joinPoint) {
		
		String method = joinPoint.getSignature().getName();
		if(!method.contentEquals("createAccount") && !method.contentEquals("createAccountConfirm")) {
			System.out.println("Hi");
			action = method.substring(0, method.length()-6);
			description = "Initiated "+ method;
			Object[] args = joinPoint.getArgs();
			for(Object arg:args) {
				if(arg instanceof HttpServletRequest) {
					HttpServletRequest request = (HttpServletRequest)arg;
					amount = Integer.parseInt(request.getParameter("Amount"));
					loginUser = request.getUserPrincipal().getName();
//					balance = Integer.parseInt(request.getParameter("AccountNumber"));
//					accountNumber = Integer.parseInt(request.getParameter("AccountNumber"));
//					description += " " + request.getParameter("Amount");
					date = getCurrentDate();
				}
			}
//			historyRecord = new History(loginUser, accountNumber, "UI", description,
//					action, beforeBalance, beforeBalance, getCurrentDate(),amount);
//			historyService.saveHistory(historyRecord); 
		}
	}
	
	@AfterReturning(pointcut = "com.spring.project.bankapp.aspects.AspectExpressions.aspectForGetAccount()"
			, returning = "acc")
	public void afterRetrievingAccount(JoinPoint joinPoint,Account acc) {
		if(acc instanceof Account) {
//			accountHolderName = acc.getAccountHolder();	
			beforeBalance = acc.getBalance();;
//			accountNumber = acc.getAccountNumber();
//			date = getCurrentDate();
//			description = "Account details";
		}	
//		historyRecord = new History(loginUser, accountNumber, accountHolderName, description, balance, date);
//		historyService.saveHistory(historyRecord);
	}
	
	@Before("com.spring.project.bankapp.aspects.AspectExpressions.aspectForSaveAccount()")
	public void beforeSave(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		for(Object arg:args) {
			if(arg instanceof Account) {
				Account acc = (Account)arg;
				accountHolderName = acc.getAccountHolder();
				accountNumber = acc.getAccountNumber();
				date = getCurrentDate();
//				beforeBalance = acc.getBalance();
				description = "Account details";
			}
		}
		
//		historyRecord = new History(loginUser, accountNumber, accountHolderName, description, action, 
//				beforeBalance, afterbalance, getCurrentDate(),amount);
//		historyService.saveHistory(historyRecord);
	}
	
	@AfterReturning(pointcut = "com.spring.project.bankapp.aspects.AspectExpressions.aspectForSaveAccount()")
	public void afterSave(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		for(Object arg:args) {
			if(arg instanceof Account) {
				Account acc = (Account)arg;
				accountHolderName = acc.getAccountHolder();
				accountNumber = acc.getAccountNumber();
				date = getCurrentDate();
				description = "Account Updated Successfully";
				afterbalance = acc.getBalance();
			}
		}
		
		historyRecord = new History(loginUser, accountNumber, accountHolderName, description, action, 
				beforeBalance, afterbalance, getCurrentDate(),amount);
		historyService.saveHistory(historyRecord);
	}
	
	@AfterReturning(pointcut = "com.spring.project.bankapp.aspects.AspectExpressions.aspectForAccountErrors()"
			, returning = "acc")
	public void accountErrors(JoinPoint joinPoint,Account acc) {
		description = joinPoint.getSignature().getName();
		if(acc instanceof Account) {
			accountHolderName = acc.getAccountHolder();
			accountNumber = acc.getAccountNumber();
			date = getCurrentDate();
		}	
		historyRecord = new History(loginUser, accountNumber, accountHolderName, description, action, 
				beforeBalance, beforeBalance, getCurrentDate(),amount);
		historyService.saveHistory(historyRecord);
	}

	private String getCurrentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);  
	}
	
}
