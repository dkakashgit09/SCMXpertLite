package com.scm.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.scm.entity.DataStream;

public interface ScmDataStreamRepository extends MongoRepository<DataStream, Long> {

}
