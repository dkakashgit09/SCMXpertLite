package com.scm.entity;

import javax.validation.constraints.Email;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "shipment")
public class Shipment 
{
	@Id
	private long shipmentNumber;
	private long containerNumber;
	private String route;
	private String goods;
	private String device;
	private String deliveryDate;
	private long poNumber;
	private long deliveryNumber;
	private long ndcNumber;
	private long batchId;
	private long serialNumberOfGoods;
	private String description;
	
	@Email
    private String email; // Email address associated with the shipment

}
