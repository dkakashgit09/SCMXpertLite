package com.scm.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entity.ScmRoles;
import com.scm.entity.ScmUsers;
import com.scm.payload.request.LoginRequest;
import com.scm.payload.request.SignRequest;
import com.scm.payload.response.LoginResponse;
import com.scm.repo.ScmUsersRepository;
import com.scm.security.JwtUtils;

@Service
public class AuthorizationServiceImplementation implements AuthorizationService 
{
	@Autowired
	private ScmUsersRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	private static Logger logger = LoggerFactory.getLogger(AuthorizationServiceImplementation.class);

	@Override
	public ResponseEntity<?> loginIn(LoginRequest loginRequest) 
	{
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		if(authentication.isAuthenticated())
		{
			ScmUsers findByEmail = userRepo.findByEmail(email).get();
			Set<String> setOfRoles = findByEmail.getRoles().stream().map(roles -> roles.getRole()).collect(Collectors.toSet());
			
			LoginResponse response = new LoginResponse();
			BeanUtils.copyProperties(findByEmail, response);
			response.setRoles(setOfRoles);
			response.setToken(jwtUtils.generateToken(email, setOfRoles));
			
			logger.info("User login Success " + email);
			return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
		}
		else
		{
			throw new UsernameNotFoundException("invalid user request !");
		}

	}

	@Override
	public ResponseEntity<?> signUp(SignRequest signupRequest) 
	{
		Optional<ScmUsers> findByEmail = userRepo.findByEmail(signupRequest.getEmail());
		System.out.println(findByEmail);
		if(findByEmail.isEmpty())
		{
			ScmUsers users = new ScmUsers();
			BeanUtils.copyProperties(signupRequest, users);
			users.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

				ScmRoles roles = new ScmRoles();
				roles.setRole("USER");
				Set<ScmRoles> addRole = new HashSet<>();
				addRole.add(roles);
				users.setRoles(addRole);
			
			userRepo.save(users);
			logger.info("Account Created with Email " + signupRequest.getEmail());
			return new ResponseEntity<String>("AccountCreated", HttpStatus.OK);
		}
		else
		{
			logger.warn("Account Already Exist with this mail " + signupRequest.getEmail());
			return new ResponseEntity<String>("EmailAlreadyExist", HttpStatus.BAD_REQUEST);
		}
	}

}
