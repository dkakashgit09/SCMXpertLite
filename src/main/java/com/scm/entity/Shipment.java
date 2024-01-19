package com.scm.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "shipment")
public class Shipment 
{
	@Id
	private long shipmentNumber;
	@NotNull(message="containerNumber cannot be null")
	private long containerNumber;
	@NotNull(message="routeDetails cannot be null")
	private String routeDetails;
	@NotNull(message="goods cannot be null")
	private String goods;
	@NotNull(message="device cannot be null")
	private long device;
		
}

