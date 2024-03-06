package com.scm.services;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.scm.entity.Shipment;
import com.scm.repo.ShipmentRepository;
import com.scm.security.JwtUtils;

@Service
public class ShipmentServiceImplementation implements ShipmentService 
{
	@Autowired
	private ShipmentRepository shipmentRepo;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserDetailsServiceImplementation userDetailsService;
	
	private static Logger logger = LoggerFactory.getLogger(ShipmentServiceImplementation.class);
	
	@Override
	public ResponseEntity<?> saveShipment(Shipment shipment) 
	{
		try
		{
			boolean exists = shipmentRepo.existsByShipmentNumber(shipment.getShipmentNumber());
			if(!exists)
			{
				shipmentRepo.save(shipment);
				String message="Shipment created successfully with following number :- " + shipment.getShipmentNumber();
				logger.info("Shipment created successfully with following number :- " + shipment.getShipmentNumber());
				return new ResponseEntity<>(message, HttpStatus.OK);
			}
			else
			{
				logger.warn("Shipment already exists with number :- " +shipment.getShipmentNumber());
				return new ResponseEntity<>("Shipment already exists with number :- " +shipment.getShipmentNumber(), HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e)
		{
			logger.warn("Error Occured while Creating Shipment" + e);
            return new ResponseEntity<>("Error Occured while Creating Shipment", HttpStatus.BAD_REQUEST);
		}
		

	}

	@Override
	public ResponseEntity<?> getShipmentsByEmail(String email, HttpHeaders headers) 
	{
		try
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
				String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
			    String token = null;
			    String username = null;
			    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) 
			    {
			        token = authorizationHeader.substring(7);
			        if (jwtUtils.isTokenExpired(token)) {
			            logger.warn("Token Time Expired");
			            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
			        }
			        username = jwtUtils.extractUsername(token);
			    }

			    if (username != null) 
			    {
			    	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			    	if (jwtUtils.validateToken(token, userDetails)) 
			        {
						logger.info("Details sent Sucessfully ");
						return new ResponseEntity<>(shipments, HttpStatus.OK);
			        }
			    	else
			    	{
						logger.warn("Token Validation Failed");
						return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
			    	}

			    }
			    else
			    {
					logger.warn("Details Not Authenticated");
					return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
			    }
	        }
		}
		catch(Exception e)
		{
			logger.warn("Error Occured while Getting Shipment Data" + e);
            return new ResponseEntity<>("Error Occured while Getting Shipment Data", HttpStatus.BAD_REQUEST);
		}

	}
	
	@Override
	public ResponseEntity<?> getShipments(HttpHeaders headers) 
	{
		try
		{
			List<Shipment> allShipments = shipmentRepo.findAll();
			if(allShipments.isEmpty())
			{
				logger.warn("No details found in database");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			else
			{
				String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
			    String token = null;
			    String username = null;
			    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) 
			    {
			        token = authorizationHeader.substring(7);
			        if (jwtUtils.isTokenExpired(token)) {
			            logger.warn("Token Time Expired");
			            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
			        }
			        // If token is not expired, extract username
			        username = jwtUtils.extractUsername(token);
			    }

			    if (username != null) 
			    {
			    	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			    	if (jwtUtils.validateToken(token, userDetails)) 
			        {
						logger.info("Details sent Sucessfully ");
						return new ResponseEntity<>(allShipments, HttpStatus.OK);
			        }
			    	else
			    	{
						logger.warn("Token Validation Failed");
						return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
			    	}

			    }
			    else
			    {
					logger.warn("Details Not Authenticated");
					return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
			    }
			}
		}
		catch(Exception e)
		{
			logger.warn("Error Occured while Getting Shipment Data" + e);
            return new ResponseEntity<>("Error Occured while Getting Shipment Data", HttpStatus.BAD_REQUEST);
		}

	}

}
