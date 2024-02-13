package com.scm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entity.DataStream;
import com.scm.kafka.producer.DataProducer;
import com.scm.repo.DataStreamRepository;

@CrossOrigin
@RestController
@RequestMapping("/datastream")
public class DataStreamController 
{
	@Autowired
    DataStreamRepository streamRepo;
	@Autowired
	private DataProducer produce;
	
	@PostMapping("/device")
	public ResponseEntity<List<DataStream>> home(Model model) 
	{
        System.out.println(model.addAttribute("DeviceData", streamRepo.findAll()));
        List<DataStream> arrayList = new ArrayList<>();
        Iterable<DataStream> findAll = streamRepo.findAll();
        for (DataStream s : findAll)
        {
        	arrayList.add(s);
        }
            
        return new ResponseEntity<>(arrayList, HttpStatus.OK);
    }

}
