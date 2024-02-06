package com.scm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.scm.services.UserDetailsServiceImplementation;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig
{
	@Autowired
    private AuthTokenFilter authTokenFilter;
	@Autowired
	private AuthEntryPointJwt authEntryPointJwt;
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public UserDetailsService userDetailsService() 
	{
        return new UserDetailsServiceImplementation();
    }
	
	@Bean
    public AuthenticationProvider authenticationProvider()
	{
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception 
	{
	    return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception 
	{
		http.cors(cors -> cors.configure(http));
		http.csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests(req -> req.requestMatchers("/auth/login", "/auth/signup","/login","/signup","/shipment/create","/shipment/find").permitAll());
//		http.authorizeHttpRequests(req -> req.anyRequest().authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/userdashboard").failureForwardUrl("/admindashboard"));
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling(authentication -> authentication.authenticationEntryPoint(authEntryPointJwt));
//		http.cors().and().csrf().disable().authorizeHttpRequests().requestMatchers("/**").permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/userdashboard").failureForwardUrl("/admindashboard");
		return http.build();
	}
}
