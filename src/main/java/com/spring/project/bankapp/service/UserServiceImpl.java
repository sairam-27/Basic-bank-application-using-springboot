package com.spring.project.bankapp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.project.bankapp.models.Role;
import com.spring.project.bankapp.models.User;
import com.spring.project.bankapp.repository.RoleRepository;
import com.spring.project.bankapp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	UserRepository userRepository;
	RoleRepository roleRepository;
	
	public UserServiceImpl(UserRepository userRepository,RoleRepository rolerepository) {
		this.userRepository = userRepository;
		this.roleRepository = rolerepository;
	}

	@Override
	@Transactional
	public void saveUser(User user,String roles) {
		String []role = roles.split(",");
		Set<Role> rolesSet = new HashSet<Role>();
		for(String temp:role) {
			Role tempRole = getRole(temp);
			if(tempRole!=null) {
				rolesSet.add(tempRole);
			}
			else {
				rolesSet.add(new Role(temp));
			}
		}
		user.setRoles(rolesSet);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(int id) {
		Optional<User> res = userRepository.findById(id);
		User user=null;
		if(res.isPresent()) {
			user = res.get();
		}
		else {
			throw new RuntimeException("Employee with id : " + id + " not found");
		}
		return user;
	}

	@Override
	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public Role getRole(String roleName) {
		List<Role> roles = roleRepository.findAll();
		for(Role role: roles) {
			if(role.getName().contentEquals(roleName)) {
				return role;
			}
		}
		return null;
//		Optional<Role> res = roleRepository.findById(id);
//		Role role=null;
//		if(res.isPresent()) {
//			role = res.get();
//		}
//		else {
//			return null;
//		}
//		return role;
		
	}

	@Override
	public User getUserByUserName(String username) {
		User user = null;
		Optional<User> res = userRepository.findByUsername(username);
		if(res.isPresent()) {
			user = res.get();
		}
		else {
			throw new RuntimeException("User with username : " + username + " not found");
		}
		return user;
	}

}
