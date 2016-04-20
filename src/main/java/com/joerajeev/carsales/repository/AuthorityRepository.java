package com.joerajeev.carsales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joerajeev.carsales.entity.Authority;

/**
 * Spring Data JPA repository for the Booking entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority,Long> {

}
