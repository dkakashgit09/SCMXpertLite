package com.scm.services;

import java.util.List;

import com.scm.entity.Shipment;

public interface ShipmentService 
{
	public String saveShipment(Shipment shipment);
	public List<Shipment> getShipmentsByEmail(String email);
	public Shipment getShipment(long shipmentNumber);
}
