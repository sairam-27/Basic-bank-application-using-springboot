package com.spring.project.bankapp.configuration;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.project.bankapp.models.MyUserDetails;
import com.spring.project.bankapp.models.User;
import com.spring.project.bankapp.repository.UserRepository;


@Service
public class MyUserdetailService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		user.orElseThrow(() -> new UsernameNotFoundException("User not found! " + username));
		return user.map(MyUserDetails::new).get();	
	}

}
