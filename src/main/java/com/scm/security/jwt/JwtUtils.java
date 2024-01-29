package com.scm.security.jwt;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.scm.security.services.UserDetailsImplementation;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

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
	
    //Name of the JWT cookie, retrieved from application properties
	@Value("${scmxpert.app.jwtCookieName}")
	private String jwtCookie;
	
	//Method to retrieve JWT token from cookies in the HTTP request
	public String getJwtFromCookies(HttpServletRequest request)
	{
	    //Retrieve the JWT cookie from the request
	    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
	    
	    //If the cookie is not null, return its value (JWT token), otherwise return null
	    String result = (cookie!=null)? cookie.getValue():null;
		return result;
	}
	
	//Method to generate JWT token and create a corresponding HTTP cookie
	public ResponseCookie generateJwtCookie(UserDetailsImplementation userPrincipal)
	{
		//Generates JWT token from the username of the userPrincipal
		String jwt = generateTokenFromUsername(userPrincipal.getUsername());
		
		//Create an HTTP cookie with the JWT token and configure its attributes
	    ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt)
	    		.path("/api")//Set the path attribute of the cookie to "/api"
	    		.maxAge(24 * 60 * 60)//Set the maximum age of the cookie to 24 hours (in seconds)
	    		.httpOnly(true)//Set the HTTP-only attribute to true to prevent client-side JavaScript access
	    		.build();//Build the HTTP cookie object
	    
	    //Return the created HTTP cookie
		return cookie;
		
	}
	
	//Method to get a clean JWT cookie with null value
	public ResponseCookie getCleanJwtCookie()
	{
		//Create an HTTP cookie with a null value to "clean" the JWT cookie
	    ResponseCookie cookie = ResponseCookie.from(jwtCookie, null)
	    		.path("/api")//sets cookie's path
	    		.build();//Build the HTTP cookie object
		return cookie;
		
	}
	
	//Method to extract username from JWT token
	public String getUserNameFromJwtToken(String token)
	{
	    return Jwts.parserBuilder()
	    		.setSigningKey(key())//Set the signing key used to verify the token's signature
	    		.build()//Builds the JWT parser
	    		.parseClaimsJws(token)//Parse the JWT token and retrieves JWS representation
	    		.getBody()//Get the body (payload) of the token
	    		.getSubject();//Extract the subject (username) from the token's claims

	}
	
	//Method to retrieve the secret key for JWT token signing
	private Key key()
	{
		//Generates secret key used for JWT token signing from the configured secret string
	    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));

	}
	
	//Method to validate JWT token
	public boolean validateJwtToken(String authToken)
	{
		try
		{
			//Parse and validate the JWT token using the secret key
			Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
			//If parsing and validation are successful, return true
			return true;

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
	
	//Method to generate JWT token from username
	public String generateTokenFromUsername(String username) 
	{
		//builds and returns JWT token with specified claims
		return Jwts.builder()
				.setSubject(username)//Set the subject (username) claim, representing the user associated with the token
				.setIssuedAt(new Date())//Set the issued at time to the current time, to indicate when token was created
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))//Set the expiration time,when the token should expire.(mentioned in application.properties)
				.signWith(key(), SignatureAlgorithm.HS256)//Sign the token using the secret key with the specified signature algorithm (HS256)
				.compact();//Compact the token into its final string representation

	}

}
