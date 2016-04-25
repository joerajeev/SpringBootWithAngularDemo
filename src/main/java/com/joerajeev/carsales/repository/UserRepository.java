package com.joerajeev.carsales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joerajeev.carsales.entity.User;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User,Long> {

}
