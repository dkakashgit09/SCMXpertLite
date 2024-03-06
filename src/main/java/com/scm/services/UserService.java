package com.scm.services;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.scm.payload.request.EditRequest;
import com.scm.payload.request.LoginRequest;
import com.scm.payload.request.SignRequest;


public interface UserService 
{
	public ResponseEntity<?> loginIn(LoginRequest loginRequest);
	public ResponseEntity<?> signUp(SignRequest signupRequest);
	public ResponseEntity<?> retriveDetails(String email, HttpHeaders headers);
	public ResponseEntity<?> editUser(EditRequest editRequest, String email);
	public ResponseEntity<?> deleteUser(LoginRequest deleteRequest, String username);
	public ResponseEntity<?> retriveAll(HttpHeaders headers);
	
}
