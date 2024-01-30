package com.scm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entity.Shipment;
import com.scm.security.services.ScmShipmentService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/shipment")
public class ShipmentController 
{
	@Autowired
	ScmShipmentService shipmentService;
	
	@PostMapping("/create")
	public ResponseEntity<String> create(@RequestBody Shipment shipment)
	{
		try
		{
			String result = shipmentService.saveShipment(shipment);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/fetch")
	public ResponseEntity<?> getShipmentDetails(@RequestParam("email") String email)
	{
		try
		{
			List<Shipment> shipmentDetails = shipmentService.getShipmentsByEmail(email);
			System.out.println(shipmentDetails);
			if(shipmentDetails.isEmpty())
			{
				String errorMsg="No Records Found";
				return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(shipmentDetails, HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>("invalid details", HttpStatus.BAD_REQUEST);
		}

	}
}
