package com.joerajeev.carsales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.joerajeev.carsales.entity.Vehicle;

/**
 * Spring Data JPA repository for the Booking entity.
 */
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

    @Query("select vehicle from Vehicle vehicle where vehicle.reg = ?#{principal.username}")
    List<Vehicle> findByUserIsCurrentUser();

}
