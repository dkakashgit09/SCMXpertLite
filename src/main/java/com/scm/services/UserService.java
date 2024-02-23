package com.scm.services;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.scm.entity.ScmUsers;
import com.scm.payload.request.EditRequest;
import com.scm.payload.request.LoginRequest;
import com.scm.payload.request.SignRequest;


public interface UserService 
{
	public ResponseEntity<?> loginIn(LoginRequest loginRequest);
	public ResponseEntity<?> signUp(SignRequest signupRequest);
	public ResponseEntity<?> retriveDetails(String email, HttpHeaders headers);
	public ResponseEntity<?> editUser(EditRequest editRequest, String email, String username);
	public ResponseEntity<?> deleteUser(LoginRequest deleteRequest, String username);
	public ResponseEntity<List<ScmUsers>> retriveAll(HttpHeaders headers);
	
}
