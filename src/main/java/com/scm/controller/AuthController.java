package com.scm.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

import com.scm.entity.ERole;
import com.scm.entity.ScmRoles;
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

//Allow cross-origin requests from all origins and caching responses for 3600 seconds
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
	
	//End point for user authentication
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
	{
		System.out.println(loginRequest.getUsername());
		System.out.println(loginRequest.getPassword());
		//Attempt to authenticate the user with provided credentials in Front-End/PostMan API
	    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	    
	    //Set the authenticated user's information(authentication object) in the security context, to ensure that the application recognizes the user as authenticated throughout the session.
	    //This is for maintaining the user's identity and permissions across different parts of the application.
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    
	    //Retrieve user details from the authentication object
	    UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();
	    
	    //Generate JWT cookie for user authentication, It helps maintain user sessions securely across multiple requests.
	    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
	    
	    //Extract user roles from the authenticated user details
	    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
	    
	    //Return the user information and JWT cookie in the response, The JWT cookie token verifies the user's identity without requiring them to repeatedly log in for each request
	    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
		
	}
	
	//End point for user registration
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
	{
		//Check if the username already exists in the database
		if(userRepo.existsByUsername(signUpRequest.getUsername()))
		{
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		
		//Check if the email already exists in the database
		if(userRepo.existsByEmail(signUpRequest.getEmail()))
		{
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		
		// Create new user's account
		ScmUsers user = new ScmUsers(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
		
		//Set user roles based on the request
		Set<String> strRoles = signUpRequest.getRoles();
	    Set<ScmRoles> roles = new HashSet<>();
	    
	    if(strRoles == null)
	    {
	    	//If no roles are specified, assign the default USER role
	    	ScmRoles userRole = roleRepo.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	    	roles.add(userRole);
	    }
	    else
	    {
	    	//Assign roles based on the specified roles in the request
	    	strRoles.forEach(role -> {
	    		switch(role)
	    		{
	    			case "admin":
	    				ScmRoles adminRole = roleRepo.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	    				roles.add(adminRole);
	    			break;
	    			default:
	    				ScmRoles userRole = roleRepo.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	    				roles.add(userRole);
	    				
	    		}
	    		
	    	});
	    }
	    
	    //Set user roles and save the user in the database
	    user.setRoles(roles);
	    userRepo.save(user);
	    
	    //Return a success message
	    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

	}

}
