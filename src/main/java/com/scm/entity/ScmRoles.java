package com.scm.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "scmroles")
public class ScmRoles 
{
	@Id
	private long id;
	private ERole name;
	
	public ScmRoles() 
	{
		
	}

	public ScmRoles(long id, ERole name) 
	{
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ScmRoles [id=" + id + ", name=" + name + "]";
	}
	
	
	
}
