package com.scm.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "scmroles")
public class ScmRoles 
{
	@Id
	private String role;
	
}
