package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scm.payload.request.LoginRequest;
import com.scm.payload.request.SignRequest;
import com.scm.services.AuthorizationService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Component
public class AuthController 
{
	@Autowired
	private AuthorizationService authService;
	
	@PostMapping("/login")
	public ResponseEntity<?> loginValid(@RequestParam String email, @RequestParam String password)
	{
		LoginRequest loginRequest2 = new LoginRequest();
		loginRequest2.setEmail(email);
		loginRequest2.setPassword(password);
		
		
		System.out.println("rwesadffd");

		return authService.loginIn(loginRequest2);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUpValid(@RequestBody SignRequest signupRequest)
	{
		return authService.signUp(signupRequest);
	}
	
}
