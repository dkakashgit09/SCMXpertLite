package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scm.payload.request.EditRequest;
import com.scm.payload.request.LoginRequest;
import com.scm.payload.request.SignRequest;
import com.scm.services.UserService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Component
@RequestMapping("/auth")
public class UserController 
{
	@Autowired
	private UserService userService;
		
	
	@PostMapping("/login")
	public ResponseEntity<?> loginValid(@RequestBody LoginRequest loginRequest)
	{

		return userService.loginIn(loginRequest);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUpValid(@RequestBody SignRequest signupRequest)
	{
		return userService.signUp(signupRequest);
	}
	
	@GetMapping("/account")
	public ResponseEntity<?> userDetails(@RequestParam String email, @RequestHeader HttpHeaders headers)
	{
		return userService.retriveDetails(email, headers);
	}
	
	@PostMapping("/all")
	public ResponseEntity<?> retrive(@RequestHeader HttpHeaders headers)
	{
		return userService.retriveAll(headers);
	}
	
	@PostMapping("/edituser")
	public ResponseEntity<?> editUser(@RequestBody EditRequest editRequest, @RequestParam String email)
	{
		return userService.editUser(editRequest, email);
	}
	
	@PostMapping("/deleteuser")
	public ResponseEntity<?> deleteUser(@RequestBody LoginRequest deleteRequest, @RequestParam String email)
	{
		return userService.deleteUser(deleteRequest, email);
	}
	
}
