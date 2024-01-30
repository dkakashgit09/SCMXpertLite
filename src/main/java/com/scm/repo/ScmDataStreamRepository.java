package com.scm.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scm.entity.DataStream;

@Repository
public interface ScmDataStreamRepository extends CrudRepository<DataStream, String> {

}
