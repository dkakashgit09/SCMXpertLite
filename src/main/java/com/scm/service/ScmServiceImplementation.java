package com.scm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entity.DataStream;
import com.scm.entity.ScmUsers;
import com.scm.entity.Shipment;
import com.scm.repo.ScmUsersRepository;

@Service
public class ScmServiceImplementation implements ScmService 
{
	
	@Autowired
	private ScmUsersRepository userRepo;
	
	@Override
	public ScmUsers saveUserData(ScmUsers users) 
	{
		ScmUsers user= (users.getUserName() != null && users.getPassword() != null && users.getEmail() != null) ? userRepo.save(users) : null;
		return user;
	}

	@Override
	public String loginUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shipment createShipment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataStream getDataStream(long deviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shipment getShipmentDetails(long shipmentNumber) {
		// TODO Auto-generated method stub
		return null;
	}


}
