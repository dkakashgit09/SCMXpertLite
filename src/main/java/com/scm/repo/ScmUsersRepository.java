package com.scm.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.scm.entity.ScmUsers;


public interface ScmUsersRepository extends MongoRepository<ScmUsers, String> 
{
	  Optional<ScmUsers> findByUsername(String username);

	  Boolean existsByUsername(String username);

	  Boolean existsByEmail(String email);
}
