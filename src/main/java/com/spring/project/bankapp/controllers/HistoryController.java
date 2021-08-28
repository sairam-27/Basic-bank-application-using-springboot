package com.spring.project.bankapp.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.project.bankapp.models.History;
import com.spring.project.bankapp.models.User;
import com.spring.project.bankapp.service.HistoryService;

@Controller
@RequestMapping("history")
public class HistoryController {
	
	@Autowired
	HistoryService historyService;

	@GetMapping("/list")
	public String getHistoryForUser(Model model,HttpServletRequest request)
	{
		String username = request.getUserPrincipal().getName();
		List<History> logs = historyService.getRecordOfUser(username);
		model.addAttribute("logs",logs);
		return "History/logs";
	}
}
