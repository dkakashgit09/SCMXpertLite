package com.scm.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Component class responsible for handling unauthorized access
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint
{

    //Logger for logging unauthorized access errors
	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	//This Method is when unauthorized access occurs
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException 
	{
		//Log unauthorized access error for debugging purposes
	    logger.error("Unauthorized error: {}", authException.getMessage());
	    
	    //Set response content type to JSON
	    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	    
	    //Set response status to 401 Unauthorized
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    
	    //Create a map to store error details to be returned in the response
	    final Map<String, Object> body = new HashMap<>();
	    body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
	    body.put("error", "Unauthorized");
	    body.put("message", authException.getMessage());
	    body.put("path", request.getServletPath());
	    
	    //Convert error details to JSON and write to response output stream
	    final ObjectMapper mapper = new ObjectMapper();
	    mapper.writeValue(response.getOutputStream(), body);
	}

}
