package com.scm.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.scm.entity.Shipment;

public interface ScmShipmentRepository extends MongoRepository<Shipment, Long> {

}
