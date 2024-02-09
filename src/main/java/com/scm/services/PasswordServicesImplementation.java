package com.scm.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entity.ScmUsers;
import com.scm.payload.request.ForgotPasswordRequest;
import com.scm.repo.ForgotPasswordRepositoy;
import com.scm.repo.ScmUsersRepository;

@Service
public class PasswordServicesImplementation implements PasswordServices 
{
	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
	
	@Autowired
	private ScmUsersRepository userRepo;
	
	@Autowired
	private ForgotPasswordRepositoy passwordRepo;
	@Override
	public String forgotPassword(String email) 
	{
		Optional<ScmUsers> userOptional = userRepo.findByEmail(email);

        if (userOptional.isEmpty()) 
        {
            return "Invalid email id";
        }
        else
        {
        	ScmUsers user = userOptional.get();
        	ForgotPasswordRequest userPasswordCollection = new ForgotPasswordRequest();
        	userPasswordCollection.setEmail(email);
        	userPasswordCollection.setUsername(user.getUsername());
        	userPasswordCollection.setRoles(user.getRoles());
        	userPasswordCollection.setToken(generateToken());
        	userPasswordCollection.setTokenCreationDate(LocalDateTime.now());

        	userPasswordCollection = passwordRepo.save(userPasswordCollection);

            return userPasswordCollection.getToken();
        }
        
	}

	@Override
	public String resetPassword(String token, String password) 
	{
		ForgotPasswordRequest userOptional = passwordRepo.findByToken(token);
		
        if (userOptional == null) 
        {
        	return "Invalid password";
        }
        
        LocalDateTime tokenCreationDate = userOptional.getTokenCreationDate();
        if(isTokenExpired(tokenCreationDate)) 
        {
        	return "Token expired.";
        }
        
        ScmUsers user = userRepo.findByUsername(userOptional.getUsername());
    	user.setPassword(password);
        userOptional.setToken(null);
        userOptional.setTokenCreationDate(null);
        passwordRepo.save(userOptional);
        userRepo.save(user);
        
        return "Your password has been successfully updated.";
	}
	
	private String generateToken() 
	{
        StringBuilder token = new StringBuilder();
        return token.append(UUID.randomUUID().toString()).append(UUID.randomUUID().toString()).toString();
    }
	
	private boolean isTokenExpired(final LocalDateTime tokenCreationDate) 
	{
        LocalDateTime now = LocalDateTime.now();
        Duration difference = Duration.between(tokenCreationDate, now);
        return difference.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }
}
