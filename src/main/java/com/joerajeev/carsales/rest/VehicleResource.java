package com.joerajeev.carsales.rest;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joerajeev.carsales.entity.Vehicle;
import com.joerajeev.carsales.repository.VehicleRepository;

/**
 * 
 * @author Rajeev
 *
 */
@RestController
@RequestMapping("/api")
public class VehicleResource {

	Logger log = Logger.getLogger(VehicleResource.class.toString());
	
	@Autowired
	private VehicleRepository vehicleRepo;
	
	/**
	 * GET  /cars -> get all the vehicles.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/cars",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Vehicle>> listAvailableCars() {
        return new ResponseEntity<>(vehicleRepo.findAll(), HttpStatus.OK);
    }
	
	/**
	 * POST /cars -> create a vehicle.
	 * 
	 * @param vehicle	Vehicle to be created.
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/cars",
		        method = RequestMethod.POST,
		        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createCar(@RequestBody Vehicle vehicle, BindingResult result) {
		if(!result.hasErrors()) {
			log.info("Vehicle: "+vehicle);
			try {
				vehicleRepo.save(vehicle);
				return new ResponseEntity<>(HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			for (ObjectError error : result.getAllErrors()) {
				log.warning(error.toString());
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	 /**
     * GET  /cars/:id -> get a car by it's registration number.
     */
    @RequestMapping(value = "/cars/{reg}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vehicle> getVehicle(@PathVariable String reg) {
        log.log(Level.FINE, "REST request to get Vehicle : {}", reg);
        Vehicle vehicle = vehicleRepo.findOne(reg);
        return Optional.ofNullable(vehicle)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
