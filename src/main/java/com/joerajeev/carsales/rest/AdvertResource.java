package com.joerajeev.carsales.rest;

import java.net.URI;
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

import com.joerajeev.carsales.entity.Advert;
import com.joerajeev.carsales.repository.AdvertRepository;

/**
 * 
 * @author Rajeev
 *
 */
@RestController
@RequestMapping("/api")
public class AdvertResource {

	Logger log = Logger.getLogger(AdvertResource.class.toString());
	
	@Autowired
	private AdvertRepository advertRepo;
	
	@RequestMapping(value = "/ads",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Advert>> listAvailableAdverts() {
        return new ResponseEntity<>(advertRepo.findAll(), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/ads",
		        method = RequestMethod.POST,
		        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Advert> createAdvert(@RequestBody Advert advert, BindingResult result) {
		if(!result.hasErrors()) {
			log.info("Advert: "+advert);
			try {
				Advert createdAdvert = advertRepo.save(advert);
				//return new ResponseEntity<>(HttpStatus.CREATED);
				return ResponseEntity.created(new URI(String.valueOf(createdAdvert.getId())))
						.body(createdAdvert);
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
     * GET  /ads/:id -> get ad by id.
     */
    @RequestMapping(value = "/ads/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Advert> getVehicle(@PathVariable int id) {
        log.log(Level.FINE, "REST request to get Advert: {}", id);
        Advert matchingVehicles = advertRepo.findOne(id);
        return Optional.ofNullable(matchingVehicles)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
