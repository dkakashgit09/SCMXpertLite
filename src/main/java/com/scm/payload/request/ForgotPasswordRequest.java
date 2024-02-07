package com.scm.payload.request;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.scm.entity.ScmRoles;

import lombok.Data;

@Data
@Document(collection = "forgot_password_requests")
public class ForgotPasswordRequest 
{
	private String email;
	private String username;
	private Set<ScmRoles> roles;
	private String token;
    private LocalDateTime tokenCreationDate;
}
