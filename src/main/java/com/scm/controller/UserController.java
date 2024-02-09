package com.scm.controller;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entity.ScmUsers;
import com.scm.payload.request.EditRequest;
import com.scm.payload.request.ForgotPasswordRequest;
import com.scm.payload.request.LoginRequest;
import com.scm.payload.request.SignRequest;
import com.scm.payload.response.ForgetPasswordResponse;
import com.scm.services.PasswordServices;
import com.scm.services.UserService;

import jakarta.mail.MessagingException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Component
public class UserController 
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordServices passwordService;
	
	@Autowired
    MailController mailController;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/login")
	public ResponseEntity<?> loginValid(@RequestBody LoginRequest loginRequest)
	{
		Object res=userService.loginIn(loginRequest);

		return (ResponseEntity<?>) res;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUpValid(@RequestBody SignRequest signupRequest)
	{
		return userService.signUp(signupRequest);
	}
	
	@PostMapping("/account")
	public ResponseEntity<?> userDetails(@RequestParam("email") String email)
	{
		try
		{
			Optional<ScmUsers> user = userService.retriveDetails(email);
			if(user.isEmpty())
			{
				String errorMessage="No User Found";
				return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
			}
			else
			{
				return new ResponseEntity<>(user, HttpStatus.OK);
			}
		}
		catch(Exception e)
		{
			return new ResponseEntity<>("invalid details to fetch", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/edituser")
	public ResponseEntity<?> editUser(@RequestBody EditRequest editRequest, @RequestParam("email") String email)
	{
		return userService.editUser(editRequest, email);
	}
	
	@PostMapping("/deleteuser")
	public ResponseEntity<?> deleteUser(@RequestBody LoginRequest deleteRequest, @RequestParam("username") String username)
	{
		return userService.deleteUser(deleteRequest, username);
	}
	
	@PostMapping("/forgetpassword")
	public ResponseEntity<ForgetPasswordResponse> forgotPassword(@RequestParam(required = false) String email)
	{
		System.out.println(email);

        String response = passwordService.forgotPassword(email);
        System.out.println(response);
//        
        if (response.equals("Invalid email id")) 
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } 
        else 
        {
            try 
            {
                mailController.sendEmail(email, response);
            } 
            catch(UnsupportedEncodingException | MessagingException e) 
            {

                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
//            
            System.out.println(response);
            return new ResponseEntity(response, HttpStatus.OK);
        }
	}
	
	@PostMapping("/resetpassword")
	public ResponseEntity<ForgotPasswordRequest> resetPassword(@RequestParam(required = false) String token, @RequestParam String password) 
	{
		System.out.println(token);
		System.out.println(password);
		
		String resetPassword = passwordService.resetPassword(token, bCryptPasswordEncoder.encode(password));
	    if (resetPassword.equals("Invalid password")) 
	    {
	    	return new ResponseEntity(HttpStatus.BAD_REQUEST);
	    } 
	    else 
	    {
	    	return new ResponseEntity(resetPassword, HttpStatus.OK);
	    }
	 }
	
}
