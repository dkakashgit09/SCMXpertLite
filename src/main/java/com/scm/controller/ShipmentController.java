package com.scm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entity.Shipment;
import com.scm.payload.request.ShipmentDeleteRequest;
import com.scm.payload.request.ShipmentEditRequest;
import com.scm.services.ShipmentService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/shipment")
public class ShipmentController 
{
	@Autowired
	private ShipmentService shipmentService;
	
	
	@PostMapping("/create")
	public ResponseEntity<?> signUpValid(@RequestBody Shipment shipment)
	{
		
		return shipmentService.saveShipment(shipment);
	}
	
	@GetMapping("/find")
    public ResponseEntity<?> getShipmentsByEmail(@RequestParam String email, @RequestHeader HttpHeaders headers) 
	{
		return shipmentService.getShipmentsByEmail(email, headers);
    }
	
	@GetMapping("/findall")
	public ResponseEntity<List<Shipment>> getAll(@RequestHeader HttpHeaders headers)
	{
		return shipmentService.getShipments(headers);
	}
	
	@PostMapping("/edit")
	public ResponseEntity<?> editShipments(@RequestBody ShipmentEditRequest shipment, @RequestParam String email)
	{
		return shipmentService.editShipment(shipment, email);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> deleteShipment(@RequestBody ShipmentDeleteRequest deleteRequest, @RequestParam String email)
	{
		return shipmentService.deleteShipment(deleteRequest, email);
	}
}
