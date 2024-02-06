package com.scm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entity.Shipment;
import com.scm.services.ShipmentService;

@CrossOrigin(origins = "*")
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
	
	@PostMapping("/find")
    public ResponseEntity<?> getShipmentsByEmail(@RequestParam("email") String email) 
	{
        System.out.println(email);
        try {
            List<Shipment> shipments = shipmentService.getShipmentsByEmail(email);
            System.out.println(shipments);
            if (shipments.isEmpty()) {
                String errorMessage="No records found";
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(shipments, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("invalid details to fetch", HttpStatus.BAD_REQUEST);
        }
    }
	
}
