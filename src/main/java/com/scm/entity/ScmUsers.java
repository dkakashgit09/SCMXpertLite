package com.scm.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "scmusers")
public class ScmUsers 
{
	@Id
	private String id;
	@NotBlank
	private String username;
	@NotBlank
    @Email
	private String email;
	@NotBlank
	private String password;
	private String token;
	//Set of roles assigned to the user, mapped as references to ScmRoles documents in MongoDB
	@DBRef
	private Set<ScmRoles> roles = new HashSet<>();
	
	//Date and time when the token was created
	private LocalDateTime tokenCreationDate; 
	
	public ScmUsers() 
	{
		
	}
	
	public ScmUsers(String username, String email, String password) 
	{
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Set<ScmRoles> getRoles() {
		return roles;
	}
	public void setRoles(Set<ScmRoles> roles) {
		this.roles = roles;
	}
	public LocalDateTime getTokenCreationDate() {
		return tokenCreationDate;
	}
	public void setTokenCreationDate(LocalDateTime tokenCreationDate) {
		this.tokenCreationDate = tokenCreationDate;
	}
		
}
