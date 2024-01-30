package com.scm.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "scmroles")
public class ScmRoles 
{
	@Id
	private String id;
	private ERole name;
	List<String> url;

	public ScmRoles() 
	{
		
	}

	public ScmRoles(ERole name) 
	{
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
	
	public List<String> getUrl() {
		return url;
	}

	public void setUrl(List<String> url) {
		this.url = url;
	}
	
	
}
