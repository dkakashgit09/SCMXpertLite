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
public class DataStream 
{
	@Id
	private long deviceId;
	@NotNull(message="Battery level cannot be null")
	private double batteryLevel;
	@NotNull(message="Temperature cannot be null")
	private String temperature;
	@NotNull(message="To Route cannot be null")
	private String routeFrom;
	@NotNull(message="From Route cannot be null")
	private String routeTo;
	@NotNull(message="Time Stamp cannot be null")
	private String timeStamp;
		
}
