package com.scm.services;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entity.ScmRoles;
import com.scm.entity.ScmUsers;
import com.scm.entity.Shipment;
import com.scm.payload.request.EditRequest;
import com.scm.payload.request.LoginRequest;
import com.scm.payload.request.SignRequest;
import com.scm.payload.response.LoginResponse;
import com.scm.repo.ScmUsersRepository;
import com.scm.repo.ShipmentRepository;
import com.scm.security.JwtUtils;

@Service
public class UserServiceImplementation implements UserService 
{
	@Autowired
	private ScmUsersRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ShipmentRepository shipmentRepo;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImplementation userDetailsService;
	
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
			logger.warn("user not authenticated" + loginRequest.getEmail());
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
	public ResponseEntity<?> editUser(EditRequest editRequest, String email, String username) 
	{
		
		if(editRequest.getEmail().equals(email))
		{
			ScmUsers users = userRepo.findByUsername(username);
			if(users!=null)
			{
				String existedPassword=users.getPassword();
				boolean existsPassword=bCryptPasswordEncoder.matches(editRequest.getPassword(), existedPassword);
				if(existsPassword)
				{
					users.setPassword(passwordEncoder.encode(editRequest.getUpdatePassword()));
					users.setUsername(editRequest.getUsername());
					userRepo.save(users);
					logger.info("Account Updated With Given Details " + editRequest.getEmail());
					return new ResponseEntity<String>("AccountUpdated", HttpStatus.OK);
				}
				else
				{
					logger.warn("The Password You Have Entered is InCorrect");
					return new ResponseEntity<String>("IncorrectPassword", HttpStatus.BAD_REQUEST);
				}
			}
			else
			{
				System.out.println(username);
				logger.warn("Users are null");
				return new ResponseEntity<String>("usersarenull", HttpStatus.BAD_REQUEST);
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
		ScmUsers mainUser = userRepo.findByUsername(username);
		
		ScmUsers userInForm = null;
		Set<ScmRoles> rolesForm = null;
		ScmRoles roleForm = null;
		String formUserRoleName = null;
		
		if(mainUser!=null)
		{

			if(email.equals(mainUser.getEmail()))
			{
				Optional<ScmUsers> userIn = userRepo.findByEmail(email);
				
				if(userIn.isPresent())
				{
					userInForm = userIn.get();
					rolesForm = userInForm.getRoles();
					roleForm = rolesForm.iterator().next();
					formUserRoleName = roleForm.getRole();
				}
				
				if(formUserRoleName.equals("ADMIN"))
				{
					logger.warn("Single ADMIN Exists, Cant be Deleted");
					return new ResponseEntity<String>("You Dont have Authority to Delete Admin",  HttpStatus.BAD_REQUEST);
				}
				else
				{
					String existedPassword=mainUser.getPassword();
					boolean existsPassword=bCryptPasswordEncoder.matches(deleteRequest.getPassword(), existedPassword);
					if(existsPassword)
					{
						List<Shipment> listOfShipments=shipmentRepo.findByEmail(email);
						if(listOfShipments.isEmpty())
						{
							userRepo.deleteById(userInForm.getId());
							logger.info("Account Deleted Sucessfully ");
							return new ResponseEntity<String>("AccountDeleted", HttpStatus.OK);
						}
						else
						{
							logger.info("Shipment exists cannot be deleted");
							return new ResponseEntity<String>("Shipment exists with associated mail :- " + email + ", cannot be deleted", HttpStatus.BAD_REQUEST);
						}
					}
					else
					{
						logger.warn("The Password You Have Entered is InCorrect");
						return new ResponseEntity<String>("IncorrectPassword", HttpStatus.BAD_REQUEST);
					}
				}

			}
			else
			{
				Set<ScmRoles> roles = mainUser.getRoles();
				ScmRoles role = roles.iterator().next();
				String mainUserRoleName = role.getRole();
				
				if(mainUserRoleName.equals("ADMIN"))
				{
					Optional<ScmUsers> userIn = userRepo.findByEmail(email);
					if(userIn.isPresent())
					{
						userInForm = userIn.get();
						rolesForm = userInForm.getRoles();
						roleForm = rolesForm.iterator().next();
						formUserRoleName = roleForm.getRole();
					}
					
					if(formUserRoleName==null)
					{
						logger.warn("Account not found in database");
						return new ResponseEntity<String>("The account mentioned with :- " + email + " not present",  HttpStatus.BAD_REQUEST);
					}
					else if(formUserRoleName.equals("ADMIN"))
					{
						logger.warn("Single ADMIN Exists, Cant be Deleted");
						return new ResponseEntity<String>("You Dont have Authority to Delete Admin",  HttpStatus.BAD_REQUEST);
					}
					else
					{
						String existedPassword=mainUser.getPassword();
						boolean existsPassword=bCryptPasswordEncoder.matches(deleteRequest.getPassword(), existedPassword);
						if(existsPassword)
						{
							List<Shipment> listOfShipments=shipmentRepo.findByEmail(email);
							if(listOfShipments.isEmpty())
							{
								userRepo.deleteById(userInForm.getId());
								logger.info("Account Deleted Sucessfully ");
								return new ResponseEntity<String>("AccountDeleted", HttpStatus.OK);
							}
							else
							{
								logger.info("Shipment exists cannot be deleted");
								return new ResponseEntity<String>("Shipment exists with associated mail :- " + email + ", cannot be deleted", HttpStatus.BAD_REQUEST);
							}
						}
						else
						{
							logger.warn("The Password You Have Entered is InCorrect");
							return new ResponseEntity<String>("IncorrectPassword", HttpStatus.BAD_REQUEST);
						}
					}
					
				}
				else
				{
					logger.warn("The given mail is not matching with your account details :- " + email);
					return new ResponseEntity<String>("EmailNotMatching", HttpStatus.BAD_REQUEST);
				}

			}
			
		}
		else
		{
			logger.warn("The given mail does not contains any data :- " + email);
			return new ResponseEntity<String>("Given-Email-Not-Exists-In-Database", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@Override
	public ResponseEntity<?> retriveDetails(String email, HttpHeaders headers) 
	{
		Optional<ScmUsers> user = userRepo.findByEmail(email);
		if(user.isEmpty())
		{
			logger.warn("No User found with this email" + email);
			return new ResponseEntity<String>("Given-Email-Not-Exists-In-Database", HttpStatus.BAD_REQUEST);
		}
		else
		{
			String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
		    String token = null;
		    String username = null;
		    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) 
		    {
		        token = authorizationHeader.substring(7);
		        if (jwtUtils.isTokenExpired(token)) {
		            logger.warn("Token Time Expired");
		            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
		        }
		        username = jwtUtils.extractUsername(token);
		    }

		    if (username != null) 
		    {
		    	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		    	if (jwtUtils.validateToken(token, userDetails)) 
		        {
		    		logger.info("User found with this email" + email);
					return new ResponseEntity<>(user, HttpStatus.OK);
		        }
		    	else
		    	{
					logger.warn("Token Validation Failed");
					return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
		    	}

		    }
		    else
		    {
				logger.warn("Details Not Authenticated");
				return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
		    }
			
		}
		
	}

	@Override
	public ResponseEntity<List<ScmUsers>> retriveAll(HttpHeaders headers) 
	{
		List<ScmUsers> allUsers = userRepo.findAll();
		if(allUsers.isEmpty())
		{
			logger.warn("No details found in database");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
		    String token = null;
		    String username = null;
		    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) 
		    {
		        token = authorizationHeader.substring(7);
		        if (jwtUtils.isTokenExpired(token)) {
		            logger.warn("Token Time Expired");
		            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
		        }
		        username = jwtUtils.extractUsername(token);
		    }

		    if (username != null) 
		    {
		    	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		    	if (jwtUtils.validateToken(token, userDetails)) 
		        {
					logger.info("Details sent Sucessfully ");
					return new ResponseEntity<>(allUsers, HttpStatus.OK);
		        }
		    	else
		    	{
					logger.warn("Token Validation Failed");
					return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
		    	}

		    }
		    else
		    {
				logger.warn("Details Not Authenticated");
				return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
		    }
		}
	}


	
}
