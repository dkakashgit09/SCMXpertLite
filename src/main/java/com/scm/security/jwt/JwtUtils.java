package com.scm.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.scm.security.services.UserDetailsImplementation;

import io.jsonwebtoken.*;

//This component is responsible for JWT operations such as token generation, validation, and cookie handling
@Component
public class JwtUtils 
{
	//Logger for logging JWT-related messages
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	//Secret key used for signing JWT tokens, retrieved from application properties
	@Value("${scmxpert.app.jwtSecret}")
	private String jwtSecret;
	
	//JWT token expiration time in milliseconds, retrieved from application properties
	@Value("${scmxpert.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	//Method to generate JWT token
	public String generateJwtToken(Authentication authentication) 
	{
		UserDetailsImplementation userPrincipal = (UserDetailsImplementation) authentication.getPrincipal();
		//builds and returns JWT token with specified claims
		return Jwts.builder()
				.setSubject(userPrincipal.getEmail())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();

	}
	
	
	//Method to extract username from JWT token
	public String getUserNameFromJwtToken(String token)
	{
	    return Jwts.parser()
	    		.setSigningKey(jwtSecret)//Set the signing key used to verify the token's signature
	    		.parseClaimsJws(token)//Parse the JWT token and retrieves JWS representation
	    		.getBody()//Get the body (payload) of the token
	    		.getSubject();//Extract the subject (username) from the token's claims

	}
	
	//Method to validate JWT token
	public boolean validateJwtToken(String authToken)
	{
		try
		{
			//Parse and validate the JWT token using the secret key
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			//If parsing and validation are successful, return true
			return true;

		}
		catch(SignatureException e)
		{
            logger.error("Invalid JWT signature: {}", e.getMessage());
		}
		catch(MalformedJwtException e)
		{
			//Log an error message if the JWT token is malformed
			logger.error("Invalid JWT token: {}", e.getMessage());
		}
		catch(ExpiredJwtException e)
		{
			//Log an error message if the JWT token is expired
			logger.error("JWT token is expired: {}", e.getMessage());
		}
		catch(UnsupportedJwtException e)
		{
			//Log an error message if the JWT token is unsupported
			logger.error("JWT token is unsupported: {}", e.getMessage());
		}
		catch(IllegalArgumentException e)
		{
			//Log an error message if the JWT claims string is empty
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		
	    //If any exception occurs during token validation, return false
		return false;
	}
	

}
