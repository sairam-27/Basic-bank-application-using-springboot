package com.spring.project.bankapp.service;

import java.util.List;

import com.spring.project.bankapp.models.Role;
import com.spring.project.bankapp.models.User;

public interface UserService {
	
	public void saveUser(User user, String string);
	
	public List<User> getUsers();
	
	public User getUser(int id);
	
	public void deleteUser(int id);
	
	public Role getRole(String role);
	
	public User getUserByUserName(String username);
	
}

