package com.scm.payload.request;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class EditRequest
{

	private String username;
	@Email
	private String email;
	private String updatePassword;
	private String password;
	
	
}