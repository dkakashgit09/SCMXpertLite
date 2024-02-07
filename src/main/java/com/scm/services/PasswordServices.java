package com.scm.services;

public interface PasswordServices 
{
	public String forgotPassword(String email);
	public String resetPassword(String token, String password);
	
}
