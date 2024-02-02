package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entity.Shipment;
import com.scm.services.ShipmentService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/shipment")
public class ShipmentController 
{
	@Autowired
	private ShipmentService shipmentService;
	
	
	@PostMapping("/create")
	public String signUpValid(@RequestBody Shipment shipment)
	{
		String response=shipmentService.saveShipment(shipment);
		return response;
	}
	
}
