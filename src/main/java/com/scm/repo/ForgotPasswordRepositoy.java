package com.scm.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.scm.payload.request.ForgotPasswordRequest;

@Repository
public interface ForgotPasswordRepositoy extends MongoRepository<ForgotPasswordRequest, String> 
{

}
