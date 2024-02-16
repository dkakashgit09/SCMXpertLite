package com.scm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entity.DataStream;
import com.scm.repo.DataStreamRepository;

@CrossOrigin
@RestController
@RequestMapping("/datastream")
public class DataStreamController 
{
	@Autowired
    DataStreamRepository streamRepo;
	
	@GetMapping("/all")
	public ResponseEntity<List<DataStream>> getAllDataStreams() 
	{
		List<DataStream> dataStreams = streamRepo.findAll();
		return new ResponseEntity<>(dataStreams, HttpStatus.OK);
    }

}
