package com.scm.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController 
{
	@GetMapping("/login")
	public String login(Model model) 
	{
		return "login";
	}
	
	@GetMapping("/userdashboard")
	public String userDashboard(Model model) 
	{
		return "userdash";
	}
	
	@GetMapping("/admindashboard")
	public String adminDashboard(Model model) 
	{
		return "registration";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		return "signup";
	}
}
