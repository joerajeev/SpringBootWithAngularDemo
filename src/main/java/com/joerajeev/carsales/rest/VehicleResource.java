package com.joerajeev.carsales.rest;

import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joerajeev.carsales.service.CarSalesService;
import com.joerajeev.carsales.service.ServiceException;
import com.joerajeev.carsales.service.User;
import com.joerajeev.carsales.service.Vehicle;

/**
 * 
 * @author Rajeev
 *
 */
@RestController
@RequestMapping("/api")
public class VehicleResource {

	Logger logger = Logger.getLogger(VehicleResource.class.toString());
	
	@Autowired
	private CarSalesService carSalesService;
	
	@RequestMapping(value = "/cars",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Vehicle>> listAvailableCars() {
        return new ResponseEntity<>(carSalesService.getAllVehicles(), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/cars",
		        method = RequestMethod.POST,
		        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createCar(@RequestBody Vehicle vehicle, BindingResult result) {
		if(!result.hasErrors()) {
			logger.info("Vehicle: "+vehicle);
			try {
				carSalesService.createVehicle(vehicle);
				return new ResponseEntity<>(HttpStatus.CREATED);
			} catch (ServiceException e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			for (ObjectError error : result.getAllErrors()) {
				logger.warning(error.toString());
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
