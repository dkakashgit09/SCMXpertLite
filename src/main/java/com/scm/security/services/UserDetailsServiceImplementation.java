package com.scm.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scm.entity.ScmUsers;
import com.scm.repo.ScmUsersRepository;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService 
{
	@Autowired
	ScmUsersRepository userRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		ScmUsers user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
	    return UserDetailsImplementation.build(user);
	}

}
