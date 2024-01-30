package com.scm.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.scm.security.services.UserDetailsServiceImplementation;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//Filter to intercept and authenticate requests by validating JWT tokens.
public class AuthTokenFilter extends OncePerRequestFilter 
{
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImplementation userDetailsService;
	
	//Logger for logging filter actions
	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
	{
		try 
		{
            //Parse JWT token from the request
			String jwt = parseJwt(request);
			
			//Validate the JWT token
		    if (jwt != null && jwtUtils.validateJwtToken(jwt)) 
		    {
		    	//Extract username from the JWT token
		        String username = jwtUtils.getUserNameFromJwtToken(jwt);
		        //Load user details using the extracted username
		        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		        //Creates authentication token with user details
		        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		        //Set authentication details
		        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		        //Set the authentication token in the security context
		        SecurityContextHolder.getContext().setAuthentication(authentication);

		    }

		}
		catch(Exception e)
		{
			//Set the authentication token in the security context
			logger.error("Cannot set user authentication: {}", e);
		}
		
		//Proceed with the filter chain
	    filterChain.doFilter(request, response);

	}
	
	//Method to parse JWT token from the request
	private String parseJwt(HttpServletRequest request) 
	{
		String headerAuth = request.getHeader("Authorization");
		if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))
		{
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}
	
}
