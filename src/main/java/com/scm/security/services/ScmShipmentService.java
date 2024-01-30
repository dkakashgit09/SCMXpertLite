package com.scm.security.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entity.Shipment;
import com.scm.repo.ScmShipmentRepository;

@Service
public class ScmShipmentService 
{
	@Autowired
	private ScmShipmentRepository shipmentRepo;
	
	public String saveShipment(Shipment shipment)
	{
		boolean shipmentExists = shipmentRepo.existsByShipmentNumber(shipment.getShipmentNumber());
		if(!shipmentExists)
		{
			shipmentRepo.save(shipment);
			return "Shipment created successfully. "+ "Your shipment number is : "+ shipment.getShipmentNumber();
		}
		else
		{
			return "Your Shipment already exists with following number : " + shipment.getShipmentNumber();
		}
	}
	
	public List<Shipment> getShipmentsByEmail(String email) 
	{
        List<Shipment> shipments = shipmentRepo.findByEmail(email);
        if (shipments.isEmpty()) 
        {
            return Collections.emptyList();
        }
        return shipments;
    }
}
