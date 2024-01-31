package com.scm.entity;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document(collection = "scmusers")
public class ScmUsers 
{
	@Id
	private ObjectId objectId;
	@NotBlank
	private String username;
	@NotBlank
    @Email
	private String email;
	@NotBlank
	private String password;
	@DBRef
	private Set<ScmRoles> roles;
	
}
