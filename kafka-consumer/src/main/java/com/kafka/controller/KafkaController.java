package com.kafka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.entity.DataStream;
import com.kafka.service.StreamService;

@CrossOrigin
@RestController
@RequestMapping("/datastream")
public class KafkaController 
{
	@Autowired
	private StreamService streamService;
	
	@GetMapping("/all")
	public ResponseEntity<List<DataStream>> getAllDataStreams() 
	{
		return streamService.getStreamData();
    }

}
