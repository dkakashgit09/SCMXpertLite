package com.scm.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scm.payload.request.LoginRequest;
import com.scm.payload.request.SignRequest;
import com.scm.payload.response.ForgetPasswordResponse;
import com.scm.services.AuthorizationService;
import com.scm.services.PasswordServices;

import jakarta.mail.MessagingException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Component
public class AuthController 
{
	@Autowired
	private AuthorizationService authService;
	
	@Autowired
	private PasswordServices passwordService;
	
	@Autowired
    MailController mailController;
	
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
	
	@PostMapping("/forgetpassword")
	public ResponseEntity<ForgetPasswordResponse> forgotPassword(@RequestParam String email)
	{
		System.out.println(email);

        String response = passwordService.forgotPassword(email);
        
        if (response.equals("Invalid email id.")) 
        {
            return new ResponseEntity<ForgetPasswordResponse>(HttpStatus.NOT_FOUND);

        } 
        else 
        {

            try 
            {
                mailController.sendEmail(email, response);
            } 
            catch(UnsupportedEncodingException | MessagingException e) 
            {

                return new ResponseEntity<ForgetPasswordResponse>(HttpStatus.BAD_REQUEST);
            }
            
            System.out.println(response);
            return new ResponseEntity(response, HttpStatus.OK);
        }
	}
	
}
