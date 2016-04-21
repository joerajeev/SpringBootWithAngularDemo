package com.joerajeev.carsales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joerajeev.carsales.entity.Vehicle;

/**
 * Spring Data JPA repository for the Booking entity.
 */
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
	
	Vehicle findByReg(String reg);
}
