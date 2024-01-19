package com.scm.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "scmusers")
public class ScmUsers 
{
	@Id
	private String email;
	@NotNull(message="name cannot be null")
	private String fullname;
	@NotNull(message="password cannot be null")
	private String password;
	@NotNull(message="role cannot be null")
	private String role;
	
}
