package com.scm.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scm.entity.ScmUsers;
import com.scm.repo.ScmUsersRepository;

//Service class responsible for loading user details by username for authentication purposes
@Service
public class UserDetailsServiceImplementation implements UserDetailsService 
{
	@Autowired
	ScmUsersRepository userRepo;

	//This method is to load user details by username
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		//Retrieves user entity from the repository by username, or throw an exception if not found
		ScmUsers user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		//Build UserDetailsImplementation object from retrieved user entity and return it
	    return UserDetailsImplementation.build(user);
	}

}
