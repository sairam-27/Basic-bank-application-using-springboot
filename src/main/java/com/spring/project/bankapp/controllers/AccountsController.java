package com.spring.project.bankapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.spring.project.bankapp.Exceptions.AccountsErrors;
import com.spring.project.bankapp.models.Account;
import com.spring.project.bankapp.models.User;
import com.spring.project.bankapp.service.AccountService;
import com.spring.project.bankapp.service.UserService;

@Controller
@RequestMapping("/accounts")
public class AccountsController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AccountsErrors accountError;
		
	@GetMapping("/createAccount")
	public String createAccount(Model model) {
		model.addAttribute("account",new Account());
		return "accounts/createAccountForm";
	}
	
	@GetMapping("/creditAccount")
	public String creditPage(Model model) {
		model.addAttribute("account",new Account());
		return "AccountOperations/CreditToAccount";
	}
	
	@GetMapping("/debitAccount")
	public String debitPage(Model model) {
		model.addAttribute("account",new Account());
		return "AccountOperations/DebitToAccount";
	}
	
	@GetMapping("/transferFunds")
	public String ctransferPage(Model model) {
		model.addAttribute("account",new Account());
		return "AccountOperations/TransferToAccount";
	}
	
	@PostMapping("/create")
	public ModelAndView createAccountConfirm(@ModelAttribute Account account, HttpServletRequest request) {
		String username = request.getUserPrincipal().getName();
		User user = userService.getUserByUserName(username);
		account.setUsers(user);
		account.setAccountHolder(username);
		account.setBalance(0);
		accountService.saveAccount(account);
		account.setAccountNumber(account.getId());
		accountService.saveAccount(account);
		ModelAndView modelAndView = new ModelAndView("Index/index");
		modelAndView.addObject("message", "Successfully created an account! \n Account number: "+account.getAccountNumber());
		return modelAndView;
//		return "Index/index";
	}
	
	@RequestMapping("/credit")
	public ModelAndView creditAmount(HttpServletRequest request,RedirectAttributesModelMap redirectAttributes) {
		int accNum = Integer.parseInt(request.getParameter("AccountNumber"));
		Account acc = accountService.getAccountByAccountNumber(accNum);
		int amount = Integer.parseInt(request.getParameter("Amount"));
		if(acc!=null) {
			if(!isCreditLlimitReached(amount)) {
				creditToAccount(accNum, amount);
//				return "redirect:/accounts/creditAccount";
				ModelAndView modelAndView = new ModelAndView("AccountOperations/CreditToAccount");
				modelAndView.addObject("message", "Successfully credited "+amount+" to account: "+acc.getAccountNumber());
				return modelAndView;

			}else {
				accountError.MaxCreditAmountReached(acc);
//				redirectAttributes.addFlashAttribute("CreditLimit","Credit Limit Reached!!");
//				return "redirect:/accounts/creditAccount";
				ModelAndView modelAndView = new ModelAndView("AccountOperations/CreditToAccount");
				modelAndView.addObject("message", "Credit Limit Reached!!");
				return modelAndView;

			}
		}
		else {
			ModelAndView modelAndView = new ModelAndView("AccountOperations/CreditToAccount");
			modelAndView.addObject("message", "Account does not exist");
			return modelAndView;
		}
	}

	private void creditToAccount(int accNum, int amount) {
		Account acc = accountService.getAccountByAccountNumber(accNum);
		acc.setBalance(acc.getBalance()+amount);
		accountService.saveAccount(acc);
	}
	
	public boolean isCreditLlimitReached(int amount) {
		return amount >=10000;
	}
		
	@RequestMapping("/debit")
	public ModelAndView debitAmount(HttpServletRequest request,RedirectAttributesModelMap redirectAttributes){
		int accNum = Integer.parseInt(request.getParameter("AccountNumber"));
		Account acc = accountService.getAccountByAccountNumber(accNum);
		int amount = Integer.parseInt(request.getParameter("Amount"));
		if(acc!=null) {
			if(requiredBalancePresent(amount,accNum)) {
				debitFromAccount(accNum,amount);
				ModelAndView modelAndView = new ModelAndView("AccountOperations/DebitToAccount");
				modelAndView.addObject("message", "Successfully debited "+amount+" from account: "+acc.getAccountNumber());
				return modelAndView;
			}
			else{
				accountError.InsufficientFunds(acc);
				ModelAndView modelAndView = new ModelAndView("AccountOperations/DebitToAccount");
				modelAndView.addObject("message", "Insufficient Funds!!");
				return modelAndView;
			}
		}
		else {
			ModelAndView modelAndView = new ModelAndView("AccountOperations/CreditToAccount");
			modelAndView.addObject("message", "Account does not exist");
			return modelAndView;
		}
		
	}
	
	private void debitFromAccount(int accNum, int amount) {
		Account acc = accountService.getAccountByAccountNumber(accNum);
		acc.setBalance(acc.getBalance()-amount);
		accountService.saveAccount(acc);
	}
	
	public boolean requiredBalancePresent(int amount,int accNum) {
		Account acc = accountService.getAccountByAccountNumber(accNum);
		return acc.getBalance()-amount>=0;
	}
	
	@RequestMapping("/transfer")
	public ModelAndView transferAmount(HttpServletRequest request) {
		int fromAccNum = Integer.parseInt(request.getParameter("fromAccountNumber"));
		int toAccNum = Integer.parseInt(request.getParameter("toAccountNumber"));
		Account fromAcc = accountService.getAccountByAccountNumber(fromAccNum);
		Account toAcc = accountService.getAccountByAccountNumber(toAccNum);
		int amount = Integer.parseInt(request.getParameter("Amount"));
		if(fromAcc!=null && toAcc!=null) {
			if(!isCreditLlimitReached(amount) && requiredBalancePresent(amount, fromAccNum)) {
				debitFromAccount(fromAccNum, amount);
				creditToAccount(toAccNum, amount);
				ModelAndView modelAndView = new ModelAndView("AccountOperations/TransferToAccount");
				modelAndView.addObject("message", "Successfully transfered "+amount+" from account:"+fromAccNum
							+"to account: "+toAccNum);
				return modelAndView;
			}
			else {
				ModelAndView modelAndView = new ModelAndView("AccountOperations/TransferToAccount");
				modelAndView.addObject("message", "Insufficient Funds!! or Credit Limit Reached!!");				
				return modelAndView;
			}
		}
		else {
			ModelAndView modelAndView = new ModelAndView("AccountOperations/CreditToAccount");
			modelAndView.addObject("message", "Account does not exist");
			return modelAndView;
		}
		
	}

}
