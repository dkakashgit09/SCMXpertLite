package com.scm.payload.response;

import java.util.Set;

import lombok.Data;

@Data
public class LoginResponse 
{
    private String email;
	private String username;
	private Set<String> roles;
	private String token;
	
}
