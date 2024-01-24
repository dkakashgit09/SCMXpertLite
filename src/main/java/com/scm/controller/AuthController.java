package com.scm.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entity.ScmUsers;
import com.scm.payload.request.LoginRequest;
import com.scm.payload.request.SignupRequest;
import com.scm.payload.response.MessageResponse;
import com.scm.payload.response.UserInfoResponse;
import com.scm.repo.ScmRoleRepository;
import com.scm.repo.ScmUsersRepository;
import com.scm.security.jwt.JwtUtils;
import com.scm.security.services.UserDetailsImplementation;

import jakarta.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController 
{
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	ScmUsersRepository userRepo;
	
	@Autowired
	ScmRoleRepository roleRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
	{
	    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	    
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    
	    UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();
	    
	    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

	    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
	    
	    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
		
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
	{
		if(userRepo.existsByUsername(signUpRequest.getUsername()))
		{
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		
		if(userRepo.existsByEmail(signUpRequest.getEmail()))
		{
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		return null;
		
		// Create new user's account
	}

}
