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
	private Long id;
	@NotBlank
	@Size(max = 30)
	private String userName;
	@NotBlank
	@Size(max = 45)
	private String email;
	@NotBlank
	@Size(max = 30)
	private String password;
	@DBRef
	private Set<ScmRoles> roles = new HashSet<>();
	
	public ScmUsers() 
	{
		
	}
	public ScmUsers(Long id, @NotBlank @Size(max = 30) String userName, @NotBlank @Size(max = 45) String email, @NotBlank @Size(max = 30) String password, Set<ScmRoles> roles) 
	{
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	@Override
	public String toString() {
		return "ScmUsers [id=" + id + ", userName=" + userName + ", email=" + email + ", password=" + password
				+ ", roles=" + roles + "]";
	}
	
	
	
}
