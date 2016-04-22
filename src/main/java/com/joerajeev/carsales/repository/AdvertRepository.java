package com.joerajeev.carsales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joerajeev.carsales.entity.Advert;

/**
 * Spring Data JPA repository for the Advert entity.
 */
public interface AdvertRepository extends JpaRepository<Advert,Integer> {
	
}
