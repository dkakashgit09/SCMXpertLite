package com.scm.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.scm.entity.ScmUsers;

@Repository
public interface ScmUsersRepository extends MongoRepository<ScmUsers, String> 
{
	  ScmUsers findByUsername(String username);
	  Optional<ScmUsers> findByEmail(String email);
	  Boolean existsByUsername(String username);
	  Boolean existsByEmail(String email);
	  long countByRolesContaining(String string);
}
