package com.scm.entity;

import java.util.Date;

import javax.validation.constraints.Email;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "scmusers")
public class Shipment 
{
	@Id
	private long shipmentNumber;
	private long containerNumber;
	private String RouteDetails;
	private String GoodsType;
	private String Device;
	private Date DeliveryDate;
	private long ndcNumber;
	private long BatchId;
	private long noOfGoods;
	private String description;
	
	@Email
    private String email; // Email address associated with the shipment

}
