package com.joerajeev.carsales.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.joerajeev.carsales.dao.CarsDao;
import com.joerajeev.carsales.dao.UserDao;

public class AdServiceTest {

	@InjectMocks
	private CarSalesService adService;
	
	@Mock
	private CarsDao carsDao;
	
	@Mock
	private UserDao userDao;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateVehicle() {
		Vehicle vehicle = createVehicle1WithData();
		
		try {
			when(carsDao.create(vehicle)).thenReturn(Boolean.TRUE);
			boolean result = adService.createVehicle(vehicle);
			assertTrue(result);
		}catch(ServiceException ex) {
			fail("Service Exception :"+ex.getMessage());
		}catch(Exception e) {
			fail("Error creating test "+e.getMessage());
		}
	}

	@Test
	public void testGetAllVehicles() {
		
		List<Vehicle> mockVehicles = new ArrayList<Vehicle>();
		mockVehicles.add(createVehicle1WithData());
		mockVehicles.add(createVehicle2WithData());
		
		when(carsDao.getAllVehicles()).thenReturn(mockVehicles);
		
		List<Vehicle> vehicles = adService.getAllVehicles();
		
		assertEquals("Did not return correct number of vehicles", 2, vehicles.size());
	}
	
	private Vehicle createVehicle1WithData() {
		Vehicle vehicle = new Vehicle();
		vehicle.setColour("Silver");
		vehicle.setMake("Honda");
		vehicle.setMilage(40000);
		vehicle.setModel("Civic");
		vehicle.setOwner(10);	//will not matter that he doesnt exist as we are mocking the dao
		vehicle.setReg("RBM123");
		vehicle.setYear(2012);
		return vehicle;
	}
	
	private Vehicle createVehicle2WithData() {
		Vehicle vehicle = new Vehicle();
		vehicle.setColour("Black");
		vehicle.setMake("Subaru");
		vehicle.setMilage(60000);
		vehicle.setModel("Impreza");
		vehicle.setOwner(2);	//will not matter that he doesnt exist as we are mocking the dao
		vehicle.setReg("GAGA123");
		vehicle.setYear(2000);
		return vehicle;
	}
	
	@Test
	public void createUserTest() {
		User user = createUser1WithData();
		try {
			when(userDao.create(user)).thenReturn(2);
			int id = adService.createUser(user);
			assertEquals("Incorrect user id returned", 2, id);
		} catch (ServiceException e) {
			fail("Service Exception :"+e.getMessage());
		}
	}
	
	@Test
	public void readByUsernameTest() {
		User mockUser = createUser1WithData();
		when(userDao.read("joerajeev")).thenReturn(mockUser);
		User user = adService.readByUsername("joerajeev");
		assertNotNull(user);
		assertEquals("Not the expected user", mockUser, user);
	}

	private User createUser1WithData() {
		User user = new User();
		user.setAddress("mitcham");
		user.setEmail("lanalovesfood@yahoo.com");
		user.setId(2);
		user.setName("joerajeev");
		user.setPassword("thejavadude");
		user.setPhone("12345678");
		return user;
	}
	
}
