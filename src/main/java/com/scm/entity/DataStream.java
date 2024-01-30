package com.scm.entity;

import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "shipment")
public class DataStream 
{
	private long deviceId;
	private double batteryLevel;
	private double temperature;
	private String routeFrom;
	private String routeTo;
	public long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	public double getBatteryLevel() {
		return batteryLevel;
	}
	public void setBatteryLevel(double batteryLevel) {
		this.batteryLevel = batteryLevel;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public String getRouteFrom() {
		return routeFrom;
	}
	public void setRouteFrom(String routeFrom) {
		this.routeFrom = routeFrom;
	}
	public String getRouteTo() {
		return routeTo;
	}
	public void setRouteTo(String routeTo) {
		this.routeTo = routeTo;
	}
		
}
