package com.spring.project.bankapp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;

@Entity
@Transactional
@Table(name = "History")
public class History {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String loginUser;
	
	private long accountNumber;
	
	private String accountHolderName;
	
	private String description;
	
	private String action;
	
	private int beforeBalance;
	
	private int afterBalance;
	
	private String actionDate;
	
	private int amount;
	
	public History() {
		// TODO Auto-generated constructor stub
	}

	public History(String loginUser, long accountNumber, String accountHolderName, String description,
			String action, int beforeBalance, int afterBalance, String actionDate,int amount) {
		super();
		this.loginUser = loginUser;
		this.accountNumber = accountNumber;
		this.accountHolderName = accountHolderName;
		this.description = description;
		this.action = action;
		this.beforeBalance = beforeBalance;
		this.afterBalance = afterBalance;
		this.actionDate = actionDate;
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDesription(String description) {
		this.description = description;
	}

	public int getBeforeBalance() {
		return beforeBalance;
	}

	public void setBeforeBalance(int beforeBalance) {
		this.beforeBalance = beforeBalance;
	}

	public int getAfterBalance() {
		return afterBalance;
	}

	public void setAfterBalance(int afterBalance) {
		this.afterBalance = afterBalance;
	}

	public String getActionDate() {
		return actionDate;
	}

	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}

}
