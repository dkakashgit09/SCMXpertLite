package com.scm.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.entity.ScmUsers;
import com.scm.repo.ScmUsersRepository;

//Service class responsible for loading user details by username for authentication purposes
@Service
public class UserDetailsServiceImplementation implements UserDetailsService 
{
	@Autowired
	ScmUsersRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		Optional<ScmUsers> findByEmail = userRepo.findByEmail(username);
		return findByEmail.map(UserDetailsImplementation::new).orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
	}

}
