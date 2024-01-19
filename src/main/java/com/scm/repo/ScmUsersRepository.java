package com.scm.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.scm.entity.ScmUsers;

public interface ScmUsersRepository extends MongoRepository<ScmUsers, String> {

}
