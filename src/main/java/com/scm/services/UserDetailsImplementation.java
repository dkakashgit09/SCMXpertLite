package com.scm.services;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.scm.entity.ScmUsers;

//Implementation of the UserDetails interface to represent authenticated user details
public class UserDetailsImplementation implements UserDetails
{
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;
	private Set<GrantedAuthority> authorities;
	
	//Constructor to initialize user details
	public UserDetailsImplementation(ScmUsers users) 
	{
		email = users.getEmail();
		password = users.getPassword();
		authorities = users.getRoles().stream().map(roles -> roles.getRole()).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		return authorities;
	}

	@Override
	public String getPassword() 
	{
		return password;
	}

	@Override
	public String getUsername() 
	{
		return email;
	}

	@Override
	public boolean isAccountNonExpired() 
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked() 
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() 
	{
		return true;
	}

	@Override
	public boolean isEnabled() 
	{
		return true;
	}
	

}
