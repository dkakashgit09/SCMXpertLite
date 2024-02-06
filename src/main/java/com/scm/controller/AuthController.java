package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<?> loginValid(@RequestBody LoginRequest loginRequest)
	{
		Object res=authService.loginIn(loginRequest);

		return (ResponseEntity<?>) res;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUpValid(@RequestBody SignRequest signupRequest)
	{
		return authService.signUp(signupRequest);
	}
	
}
