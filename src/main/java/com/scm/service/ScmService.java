package com.scm.service;

import com.scm.entity.DataStream;
import com.scm.entity.ScmUsers;
import com.scm.entity.Shipment;

public interface ScmService 
{
	public ScmUsers saveUserData();
	public String loginUser();
	public Shipment createShipment();
	public Shipment getShipmentDetails(long shipmentNumber);
	public DataStream getDataStream(long deviceId);
	
}
