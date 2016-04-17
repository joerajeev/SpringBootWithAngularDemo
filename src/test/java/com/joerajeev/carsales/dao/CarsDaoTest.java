package com.joerajeev.carsales.dao;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.joerajeev.carsales.service.Vehicle;

/**
 * Test class for CarsDao
 *
 *TODO: 
 * -You can use spring to rollback the db changs yes?
 * -I need to not depend on UserDao and AuthorityDao. Can I mock using spring?
 * 
 * @author Rajeev
 *
 */
public class CarsDaoTest {

	@Autowired
	private CarsDao carsDao;
	
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testCreateAndGetAllVehicles() {
		Vehicle toyota = new Vehicle();
		//toyota.set
		//carsDao.create(car)
	}


}
