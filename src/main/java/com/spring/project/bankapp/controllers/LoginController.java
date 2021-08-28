package com.spring.project.bankapp.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.project.bankapp.models.Account;
import com.spring.project.bankapp.models.Role;
import com.spring.project.bankapp.models.User;
import com.spring.project.bankapp.repository.RoleRepository;
import com.spring.project.bankapp.service.UserService;


@Controller
public class LoginController {	
	
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public LoginController(UserService userService) {
		this.userService = userService;
	}

    @GetMapping("/")
    public String home() {
        return "Index/index";
    }
    
    @GetMapping("/register")
	public String registerForm(Model model) {
    	model.addAttribute("user",new User());
        return "Index/RegistrationForm";
	} 
    
    @PostMapping("/registration")
    public ModelAndView registration(@Valid @ModelAttribute("user") User user,BindingResult br,@RequestParam(value = "User", defaultValue = "") String userRole,@RequestParam(value = "Admin",defaultValue = "") String adminRole
    		,HttpServletRequest request) {
    	if(br.hasErrors()) {
    		ModelAndView modelAndView = new ModelAndView("Index/RegistrationForm");
    		return modelAndView;
    	}
    	else {
    		StringBuffer buffer= null; 
    		System.out.println(userRole+adminRole);
    		if(!userRole.contentEquals(""))
        		buffer = new StringBuffer(userRole);
    		if(!adminRole.contentEquals("")) {
    			if(buffer!=null)
    				buffer.append(","+adminRole);
    			else 
    				buffer = new StringBuffer(adminRole);	
    		}
        	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        	user.setActive(true);
        	if(buffer == null)
        		buffer = new StringBuffer(userRole);
            userService.saveUser(user,buffer.toString());
            
            Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ModelAndView modelAndView;
            if(principal instanceof UserDetails && validLogin((UserDetails) principal)){
            	modelAndView = new ModelAndView("Index/index"); 
            	modelAndView.addObject("message", "Successfully created an user! with username: "+user.getUsername());
                return modelAndView;
            }
            
            modelAndView = new ModelAndView("Index/loginpage");
    		modelAndView.addObject("message", "Successfully created an user! with username: "+user.getUsername());
    		return modelAndView;
    	}
    }

    private boolean validLogin(UserDetails userDetails) {
        return userDetails.isAccountNonExpired() &&
                userDetails.isAccountNonLocked() &&
                userDetails.isCredentialsNonExpired() &&
                userDetails.isEnabled();
    }

	@GetMapping("/login")
	public String login(Model model) {
		return "Index/loginpage";
	}
	
}
