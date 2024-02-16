package com.scm.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.scm.entity.DataStream;

@Repository
public interface DataStreamRepository extends MongoRepository<DataStream, String> 
{

}