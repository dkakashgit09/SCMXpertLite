package com.scm.services;

import org.springframework.http.ResponseEntity;

import com.scm.payload.request.SignRequest;


public interface AuthorizationService 
{
	public ResponseEntity<?> loginIn(String email, String password);
	public ResponseEntity<?> signUp(SignRequest signupRequest);
}
