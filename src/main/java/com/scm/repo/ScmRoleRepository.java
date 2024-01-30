package com.scm.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.scm.entity.ERole;
import com.scm.entity.ScmRoles;

@Repository
public interface ScmRoleRepository extends MongoRepository<ScmRoles, String> 
{
	Optional<ScmRoles> findByName(ERole name);
	Optional<ScmRoles> findByUrl(ERole url);
}
