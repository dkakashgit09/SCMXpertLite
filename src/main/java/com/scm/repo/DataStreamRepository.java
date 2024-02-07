package com.scm.repo;

import org.springframework.data.repository.CrudRepository;

import com.scm.entity.DataStream;

public interface DataStreamRepository extends CrudRepository <DataStream, String> 
{

}
