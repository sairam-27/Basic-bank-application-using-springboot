package com.spring.project.bankapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.project.bankapp.models.User;
import com.spring.project.bankapp.service.UserService;

@Controller
@RequestMapping("/adminDashboard")
public class AdminDashboardController {
	
	@Autowired
	UserService userService ;

	@GetMapping("/getUserList")
	public String getUserList(Model model) {
		List<User> users = userService.getUsers();
		model.addAttribute("users",users);
		return "AdminDashboard/UserList";
	}
	
	@GetMapping("/updateUser")
	public String updateUser(@RequestParam("userId")  int id, Model model) {
		User user = userService.getUser(id);
		model.addAttribute("user",user);
		return "Index/RegistrationForm";
	}
	
	@GetMapping("/deleteUser")
	public String deleteUser(Model model,@RequestParam("userId")  int id) {
		userService.deleteUser(id);
		return "redirect:/adminDashboard/getUserList";
	}
	
}
