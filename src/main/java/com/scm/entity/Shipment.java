package com.scm.entity;

import javax.validation.constraints.Email;

import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "shipment")
public class Shipment 
{
	
	private long shipmentNumber; 
    private long containerNumber; 
    private String descripton; 
    private String route;
    private String goods; 
    private String country; 
    private String deliveryDate; 
    private long poNumber; 
    private long deliveryNumber; 
    private long ndcNumber; 
    private long batchId; 
    private long serialNumberOfGoods;
		
    //Email Associated with the Shipment
    @Email
    private String email;

	public long getShipmentNumber() {
		return shipmentNumber;
	}

	public void setShipmentNumber(long shipmentNumber) {
		this.shipmentNumber = shipmentNumber;
	}

	public long getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(long containerNumber) {
		this.containerNumber = containerNumber;
	}

	public String getDescripton() {
		return descripton;
	}

	public void setDescripton(String descripton) {
		this.descripton = descripton;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public long getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(long poNumber) {
		this.poNumber = poNumber;
	}

	public long getDeliveryNumber() {
		return deliveryNumber;
	}

	public void setDeliveryNumber(long deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}

	public long getNdcNumber() {
		return ndcNumber;
	}

	public void setNdcNumber(long ndcNumber) {
		this.ndcNumber = ndcNumber;
	}

	public long getBatchId() {
		return batchId;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}

	public long getSerialNumberOfGoods() {
		return serialNumberOfGoods;
	}

	public void setSerialNumberOfGoods(long serialNumberOfGoods) {
		this.serialNumberOfGoods = serialNumberOfGoods;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
    
}

