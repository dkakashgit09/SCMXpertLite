package com.scm.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;


@Document(collection = "shipment")
public class Shipment 
{
	@Id
	private long shipmentNumber;
	@NotNull(message="User ID cannot be null")
	private long userId;
	@NotNull(message="containerNumber cannot be null")
	private long containerNumber;
	@NotNull(message="routeDetails cannot be null")
	private String routeDetails;
	@NotNull(message="goods cannot be null")
	private String goods;
	@NotNull(message="device cannot be null")
	private long device;
		
}

