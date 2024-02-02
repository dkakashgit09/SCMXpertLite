package com.scm.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entity.Shipment;
import com.scm.repo.ShipmentRepository;

@Service
public class ShipmentServiceImplementation implements ShipmentService 
{
	@Autowired
	private ShipmentRepository shipmentRepo;
	@Override
	public String saveShipment(Shipment shipment) 
	{
		boolean exists = shipmentRepo.existsByShipmentNumber(shipment.getShipmentNumber());
		if(!exists)
		{
			shipmentRepo.save(shipment);
            return "Shipment created successfully with following number :- " + shipment.getShipmentNumber();
		}
		else
		{
			return "Shipment already exists with number :- " +shipment.getShipmentNumber();
		}
	}

	@Override
	public List<Shipment> getShipmentsByEmail(String email) 
	{
		List<Shipment> shipments = shipmentRepo.findByEmail(email);
        if (shipments.isEmpty()) 
        {
            return Collections.emptyList();
        }
        else
        {
        	return shipments;
        }
	}

	@Override
	public Shipment getShipment(long shipmentNumber) 
	{
		boolean exists = shipmentRepo.existsByShipmentNumber(shipmentNumber);
		if(!exists)
		{
			return null;
		}
		else
		{
			Shipment result = shipmentRepo.findByShipmentNumber(shipmentNumber);
			return result;
		}
		
	}

}
