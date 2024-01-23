package com.scm.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
	
}
