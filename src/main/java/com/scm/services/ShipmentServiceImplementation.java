package com.scm.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entity.ScmRoles;
import com.scm.entity.ScmUsers;
import com.scm.entity.Shipment;
import com.scm.payload.request.ShipmentDeleteRequest;
import com.scm.payload.request.ShipmentEditRequest;
import com.scm.repo.ScmUsersRepository;
import com.scm.repo.ShipmentRepository;

@Service
public class ShipmentServiceImplementation implements ShipmentService 
{
	@Autowired
	private ShipmentRepository shipmentRepo;
	@Autowired
	private ScmUsersRepository userRepo;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private static Logger logger = LoggerFactory.getLogger(ShipmentServiceImplementation.class);
	
	@Override
	public String saveShipment(Shipment shipment) 
	{
		boolean exists = shipmentRepo.existsByShipmentNumber(shipment.getShipmentNumber());
		if(!exists)
		{
			shipmentRepo.save(shipment);
            return "Shipment created successfully with following number :- " + shipment.getShipmentNumber();
		}
		else
		{
			return "Shipment already exists with number :- " +shipment.getShipmentNumber();
		}
	}

	@Override
	public ResponseEntity<?> getShipmentsByEmail(String email) 
	{
		List<Shipment> shipments = shipmentRepo.findByEmail(email);
        if (shipments.isEmpty()) 
        {
        	String errorMessage="No records found";
        	logger.warn("No Records found with this email" + email);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        else
        {
        	return new ResponseEntity<>(shipments, HttpStatus.OK);
        }
	}
	
	@Override
	public ResponseEntity<List<Shipment>> getShipments() 
	{
		List<Shipment> allShipments = shipmentRepo.findAll();
		if(allShipments.isEmpty())
		{
			logger.warn("No details found in database");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			logger.info("Details sent Sucessfully ");
			return new ResponseEntity<>(allShipments, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> editShipment(ShipmentEditRequest shipment, String email) 
	{
		ScmUsers userInForm = null;
		Set<ScmRoles> roles = null;
		ScmRoles role = null;
		String roleName = null;
		
		Optional<ScmUsers> user = userRepo.findByEmail(email);
		
		if(user.isPresent())
		{
			userInForm = user.get();
			roles = userInForm.getRoles();
			role = roles.iterator().next();
			roleName = role.getRole();
		}
		if(roleName.equals("ADMIN"))
		{
			Optional<Shipment> ship = shipmentRepo.findById(shipment.getShipmentNumber());
			if(ship.isPresent())
			{
				Shipment shipmentDetails = ship.get();
				shipmentDetails.setContainerNumber(shipment.getContainerNumber());
				shipmentDetails.setBatchId(shipment.getBatchId());
				shipmentDetails.setDeliveryDate(shipment.getDeliveryDate());
				shipmentDetails.setDeliveryNumber(shipment.getDeliveryNumber());
				shipmentDetails.setDescription(shipment.getDescription());
				shipmentDetails.setDevice(shipment.getDevice());
				shipmentDetails.setEmail(shipment.getEmail());
				shipmentDetails.setGoods(shipment.getGoods());
				shipmentDetails.setNdcNumber(shipment.getNdcNumber());
				shipmentDetails.setPoNumber(shipment.getPoNumber());
				shipmentDetails.setRoute(shipment.getRoute());
				shipmentDetails.setSerialNumberOfGoods(shipment.getSerialNumberOfGoods());
				shipmentDetails.setShipmentNumber(shipment.getShipmentNumber());
				
				shipmentRepo.save(shipmentDetails);
				logger.info("Details Updated Successfully");
				return new ResponseEntity<>(shipmentDetails, HttpStatus.OK);

			}
			else
			{
				logger.warn("No details found in database");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		else
		{
			logger.warn("Admin only can make changes");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}

	@Override
	public ResponseEntity<?> deleteShipment(ShipmentDeleteRequest deleteRequest, String email) 
	{
		ScmUsers userInForm = null;
		Set<ScmRoles> roles = null;
		ScmRoles role = null;
		String roleName = null;
		
		Optional<ScmUsers> user = userRepo.findByEmail(email);
		
		if(user.isPresent())
		{
			userInForm = user.get();
			roles = userInForm.getRoles();
			role = roles.iterator().next();
			roleName = role.getRole(); 
		}
		if(roleName.equals("ADMIN"))
		{
			if(email.equals(deleteRequest.getEmail()))
			{
				String existedPassword=userInForm.getPassword();
				boolean existsPassword=bCryptPasswordEncoder.matches(deleteRequest.getPassword(), existedPassword);
				if(existsPassword)
				{
					shipmentRepo.deleteById(deleteRequest.getShipmentNumber());
					logger.info("Shipment Deleted Sucessfully ");
					return new ResponseEntity<String>("Shipment Deleted", HttpStatus.OK);
				}
				else
				{
					logger.info("The Password You Have Entered is InCorrect");
					return new ResponseEntity<String>("IncorrectPassword", HttpStatus.BAD_REQUEST);
				}
			}
			else
			{
				logger.warn("The given mail is not matching with your account details :- " + deleteRequest.getEmail());
				return new ResponseEntity<String>("EmailNotMatching", HttpStatus.BAD_REQUEST);
			}
		}
		else
		{
			logger.warn("Admin only can make changes");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
