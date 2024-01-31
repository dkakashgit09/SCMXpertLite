package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scm.payload.request.SignRequest;
import com.scm.services.AuthorizationService;

@RestController
@RequestMapping("/auth")
public class AuthController 
{
	@Autowired
	private AuthorizationService authService;
	
	@PostMapping("/login")
	public ResponseEntity<?> loginValid(@RequestParam String email, @RequestParam String password)
	{
		return authService.loginIn(email, password);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUpValid(@RequestBody SignRequest loginRequest)
	{
		return authService.signUp(loginRequest);
	}
	
	@PreAuthorize("hasAnyAuthority('SA')")
	@GetMapping("/say")
	public String sa()
	{
		return "hello";
	}
}
