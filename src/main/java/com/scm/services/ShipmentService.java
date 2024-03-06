package com.scm.services;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.scm.entity.Shipment;

public interface ShipmentService 
{
	public ResponseEntity<?> saveShipment(Shipment shipment);
	public ResponseEntity<?> getShipmentsByEmail(String email, HttpHeaders headers);
	public ResponseEntity<?> getShipments(HttpHeaders headers);
}
