package com.kafka.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kafka.entity.DataStream;
import com.kafka.repo.DataStreamRepository;

@Service
public class StreamServiceImplementation implements StreamService 
{
	
	@Autowired
	private DataStreamRepository streamRepo;
	
	private static Logger logger = LoggerFactory.getLogger(StreamServiceImplementation.class);

	@Override
	public ResponseEntity<List<DataStream>> getStreamData() 
	{
		List<DataStream> allStreamData = streamRepo.findAll();
		
        if (allStreamData.isEmpty()) 
        {
			logger.warn("No details found in database");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
        {

        	logger.info("Details sent Sucessfully ");
			return new ResponseEntity<>(allStreamData, HttpStatus.OK);

        }
        
	}

}