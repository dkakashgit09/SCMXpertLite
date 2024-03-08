package com.scm.payload.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ShipmentEditRequest 
{
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
    private String email; // Email associated with the shipment
}
