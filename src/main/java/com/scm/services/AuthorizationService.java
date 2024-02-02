package com.scm.services;

import org.springframework.http.ResponseEntity;

import com.scm.payload.request.LoginRequest;
import com.scm.payload.request.SignRequest;


public interface AuthorizationService 
{
	public ResponseEntity<?> loginIn(LoginRequest loginRequest);
	public ResponseEntity<?> signUp(SignRequest signupRequest);
}
