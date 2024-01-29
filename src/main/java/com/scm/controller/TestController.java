package com.scm.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController 
{
	// This end point is accessible to all users
	@GetMapping("/all")
	 public String allAccess() 
	{
	    return "Public Content.";
	}
	
	// This end point is accessible to users with USER, MODERATOR, or ADMIN roles
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	 public String userAccess() 
	{
	    return "User Content.";
	}
	
	// This end point is accessible only to users with ADMIN role
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	 public String adminAccess() 
	{
	    return "Admin Content.";
	}
}
