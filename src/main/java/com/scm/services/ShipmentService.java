package com.scm.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.scm.entity.Shipment;
import com.scm.payload.request.ShipmentDeleteRequest;
import com.scm.payload.request.ShipmentEditRequest;

public interface ShipmentService 
{
	public ResponseEntity<?> saveShipment(Shipment shipment);
	public ResponseEntity<?> getShipmentsByEmail(String email);
	public ResponseEntity<List<Shipment>> getShipments();
	public ResponseEntity<?> editShipment(ShipmentEditRequest shipment, String email);
	public ResponseEntity<?> deleteShipment(ShipmentDeleteRequest deleteRequest, String email);
}
