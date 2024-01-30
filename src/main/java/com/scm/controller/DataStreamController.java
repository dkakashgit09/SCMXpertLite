package com.scm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entity.DataStream;
import com.scm.repo.ScmDataStreamRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/data/stream")
public class DataStreamController 
{
	@Autowired
	ScmDataStreamRepository streamRepo;
	
	 @CrossOrigin
	 @PostMapping("/devicedata")
	 @PreAuthorize("hasRole('ADMIN')")
	 public ResponseEntity<List<DataStream>> deviceList(Model model)
	 {
	        System.out.println(model.addAttribute("DeviceData", streamRepo.findAll()));
	        List<DataStream> list = new ArrayList<>();
	        Iterable<DataStream> findAll = streamRepo.findAll();
	        for(DataStream data : findAll)
	        {
	        	list.add(data);
	        }
			return new ResponseEntity<>(list, HttpStatus.OK);
	 }
}
