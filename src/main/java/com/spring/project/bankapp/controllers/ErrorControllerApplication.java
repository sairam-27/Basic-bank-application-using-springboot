package com.spring.project.bankapp.controllers;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorControllerApplication  implements ErrorController {
	
	@RequestMapping("/error")
    public String handleError(HttpServletResponse response) {
		if(response.getStatus()==HttpStatus.INTERNAL_SERVER_ERROR.value()) {
			return "errorPages/error-500";
		}
		else if(response.getStatus()==HttpStatus.NOT_FOUND.value()) {
			return "errorPages/error-404";
		}
		else if(response.getStatus()==HttpStatus.FORBIDDEN.value()) {
			return "errorPages/error-403";
		}
        return "errorPages/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
	
}
