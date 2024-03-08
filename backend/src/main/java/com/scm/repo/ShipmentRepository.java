package com.scm.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.scm.entity.Shipment;

@Repository
public interface ShipmentRepository extends MongoRepository<Shipment, Long> 
{
	Shipment findByShipmentNumber(long shipmentNumber);
    boolean existsByShipmentNumber(long shipmentNumber);
    List<Shipment> findByEmail(String email);
}
