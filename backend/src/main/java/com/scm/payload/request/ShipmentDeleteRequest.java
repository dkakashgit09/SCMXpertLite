package com.scm.payload.request;

import lombok.Data;

@Data
public class ShipmentDeleteRequest 
{
	private long shipmentNumber;
	private String email;
	private String password;
}
