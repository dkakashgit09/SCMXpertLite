package com.scm.payload.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class SignRequest
{

	private String username;
	@Email
	private String email;
	private String password;
	
	
}
