package com.scm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.scm.security.jwt.AuthEntryPointJwt;
import com.scm.security.jwt.AuthTokenFilter;
import com.scm.security.services.UserDetailsServiceImplementation;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig
{
	@Autowired
	UserDetailsServiceImplementation userDetailsService;
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	//Creates Instance which is responsible for filtering incoming HTTP requests to authenticate JWT tokens.
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter()
	{
		//Returns it as a bean, It can be injected into other Spring components.
		return new AuthTokenFilter();
	}
	
	//Responsible for authenticating users using a DAO (Data Access Object) approach.
	@Bean
	public DaoAuthenticationProvider authenticationProvider() 
	{
	    //Creates new instance of DaoAuthenticationProvider
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		//Sets user details service for the authentication provider, responsible for retrieving user details during authentication.
		authProvider.setUserDetailsService(userDetailsService);
		//Sets password encoder for the authentication provider, used to encode and verify user passwords
	    authProvider.setPasswordEncoder(passwordEncoder());
	   
	    //Returns the configured authentication provider bean. It can be injected into other Spring components for authentication purposes.
	    return authProvider;
	}
	
	//Responsible to provide access to the AuthenticationManager bean within the Spring Security configuration.
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception 
	{
		//Retrieves the AuthenticationManager instance from the AuthenticationConfiguration and returns it.
	    return authConfig.getAuthenticationManager();
	}
	
	
	//Secure way to encode passwords before storing them in a database
	@Bean
	public PasswordEncoder passwordEncoder() 
	{
		//Returns Instance used to securely hash the password
	    return new BCryptPasswordEncoder();
	}
	
	//This method is to define and configure the SecurityFilterChain for handling incoming HTTP requests
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception 
	{
		//Disable CSRF protection as we're using JWT tokens for authentication
	    http.csrf(csrf -> csrf.disable())
	    	//Configure the authentication entry point for unauthorized requests
	        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
	        //Set session management to STATELESS to ensure no session is created or used
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        //Authorize specific HTTP requests
	        .authorizeHttpRequests(auth -> 
	          //Permit all requests to "/api/auth/**" for authentication purposes
	          auth.requestMatchers("/api/auth/**").permitAll()
	          // Permit all requests to "/api/test/**" for testing purposes
	              .requestMatchers("/api/test/**").permitAll()
	        );
	    
	    //Set the authentication provider for the HTTP security configuration
	    http.authenticationProvider(authenticationProvider());
	    
	    //This filter intercepts incoming requests and performs JWT token-based authentication
	    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	    
	    //Builds and returns the configured SecurityFilterChain instance.
	    return http.build();
	}
}
