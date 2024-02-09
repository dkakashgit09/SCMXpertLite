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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entity.ScmRoles;
import com.scm.entity.ScmUsers;
import com.scm.payload.request.EditRequest;
import com.scm.payload.request.LoginRequest;
import com.scm.payload.request.SignRequest;
import com.scm.payload.response.LoginResponse;
import com.scm.repo.ScmUsersRepository;
import com.scm.security.JwtUtils;

@Service
public class UserServiceImplementation implements UserService 
{
	@Autowired
	private ScmUsersRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	private static Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);

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
	
	@Override
	public ResponseEntity<?> editUser(EditRequest editRequest, String email) 
	{
		String username=editRequest.getUsername();
		if(editRequest.getEmail().equals(email))
		{
			ScmUsers users = userRepo.findByUsername(username);
			String existedPassword=users.getPassword();
			boolean existsPassword=bCryptPasswordEncoder.matches(editRequest.getPassword(), existedPassword);
			if(existsPassword)
			{
				users.setPassword(passwordEncoder.encode(editRequest.getUpdatePassword()));
				users.setUsername(username);
				userRepo.save(users);
				logger.info("Account Updated With Given Details " + editRequest.getEmail());
				return new ResponseEntity<String>("AccountUpdated", HttpStatus.OK);
			}
			else
			{
				logger.info("The Password You Have Entered is InCorrect");
				return new ResponseEntity<String>("IncorrectPassword", HttpStatus.BAD_REQUEST);
			}
		}
		else
		{
			logger.warn("The given mail is not matching with your account details :- " + editRequest.getEmail());
			return new ResponseEntity<String>("EmailNotMatching", HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public ResponseEntity<?> deleteUser(LoginRequest deleteRequest, String username) 
	{
		String email=deleteRequest.getEmail();
		ScmUsers users = userRepo.findByUsername(username);
		if(deleteRequest.getEmail().equals(users.getEmail()))
		{
			String existedPassword=users.getPassword();
			boolean existsPassword=bCryptPasswordEncoder.matches(deleteRequest.getPassword(), existedPassword);
			if(existsPassword)
			{
				userRepo.deleteById(users.getId());
				logger.info("Account Deleted Sucessfully ");
				return new ResponseEntity<String>("AccountDeleted", HttpStatus.OK);
			}
			else
			{
				logger.info("The Password You Have Entered is InCorrect");
				return new ResponseEntity<String>("IncorrectPassword", HttpStatus.BAD_REQUEST);
			}
		}
		else
		{
			logger.warn("The given mail is not matching with your account details :- " + deleteRequest.getEmail());
			return new ResponseEntity<String>("EmailNotMatching", HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public Optional<ScmUsers> retriveDetails(String email) 
	{
		Optional<ScmUsers> user = userRepo.findByEmail(email);
		if(user.isEmpty())
		{
			logger.warn("No User found with this email" + email);
			return Optional.empty();
		}
		else
		{
			return user;
		}
		
	}


	
}
