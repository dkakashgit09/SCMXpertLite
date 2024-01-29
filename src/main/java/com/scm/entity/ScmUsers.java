package com.scm.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Document(collection = "scmusers")
public class ScmUsers 
{
	@Id
	private String id;
	@NotBlank
	@Size(max = 50)
	private String username;
	@NotBlank
	@Size(max = 70)
	private String email;
	@NotBlank
	@Size(max = 60)
	private String password;
	
	//Set of roles assigned to the user, mapped as references to ScmRoles documents in MongoDB
	@DBRef
	private Set<ScmRoles> roles = new HashSet<>();
	
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
	public String getUserName() {
		return username;
	}
	public void setUserName(String username) {
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
	public Set<ScmRoles> getRoles() {
		return roles;
	}
	public void setRoles(Set<ScmRoles> roles) {
		this.roles = roles;
	}
	
	
}
