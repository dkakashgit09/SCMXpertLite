package com.scm.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entity.ScmUsers;

@RestController
public class ScmController {
	
	@PostMapping("/signup")
	public String signUpUserData(@RequestBody ScmUsers user)
	{
		return "";
	}
	
	@PutMapping("/signin/{email}/{password}")
	public String signInUser(@PathVariable String email, @PathVariable String password)
	{
		return "";
	}

}
