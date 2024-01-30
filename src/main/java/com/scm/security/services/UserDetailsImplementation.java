package com.scm.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.scm.entity.ScmUsers;

//Implementation of the UserDetails interface to represent authenticated user details
public class UserDetailsImplementation implements UserDetails
{
	private static final long serialVersionUID = 1L;
	private String id;
	private String username;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	//Constructor to initialize user details
	public UserDetailsImplementation(String id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) 
	{
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	//Static method to build UserDetailsImplementation object from ScmUsers entity
	public static UserDetailsImplementation build(ScmUsers user)
	{
		//Map ScmRoles to GrantedAuthority objects
		List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		return new UserDetailsImplementation(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		return authorities;
	}
	
	public String getId() 
	{
	    return id;
	  
	}
	
	public String getEmail() 
	{
	    return email;
	  
	}
	
	@Override
	public String getPassword() 
	{
		return password;
	}

	@Override
	public String getUsername() 
	{
		
		return username;
	}
	
	//Below Methods represents account expiration, lock status, and credential expiration
	@Override
	public boolean isAccountNonExpired() 
	{

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() 
	{
		return true;
	}
	
	//Method to indicate whether the user account is enabled
	@Override
	public boolean isEnabled()
	{
		return true;
	}
	
	//Method to compare UserDetailsImplementation objects based on their IDs
	@Override
	public boolean equals(Object obj) 
	{
	    if(this == obj)
	    {
	      return true;
	    }
	    if(obj == null || getClass() != obj.getClass())
	    {
	      return false;
	    }
	    UserDetailsImplementation user = (UserDetailsImplementation) obj;
	    return Objects.equals(id, user.id);
	}

}
