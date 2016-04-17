package com.joerajeev.carsales.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joerajeev.carsales.Application;
import com.joerajeev.carsales.service.CarSalesService;
import com.joerajeev.carsales.service.User;
import com.joerajeev.carsales.service.Vehicle;

/**
 * Test class for the BookingResource REST controller.
 *
 * @see BookingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VehicleResourceIntTest {
	
	  /** MediaType for JSON UTF8 */
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	//Default vehicle test data
	private static final int VEHICLE_YEAR = 2014;
	private static final String OWNER_ADDRESS = "Mooroolbark";
	private static final String OWNER_CONTACT_NUMBER = "123456789";
	private static final String OWNER_PW = "asdfasdf";
	private static final String OWNER_NAME = "Joseph";
	private static final String OWNER_EMAIL = "joerajeev@gmail.com";
	private static final int OWNER_ID = 100;
	private static final String VEHICLE_REG = "REGO101";
    private static final int VEHICLE_MILAGE = 10000;
	private static final String VEHICLE_MODEL = "Civic";
	private static final String VEHICLE_MAKE = "Honda";
	private static final String VEHICLE_COLOUR = "Black";

	@Autowired
    private CarSalesService carSalesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAdMockMvc;

    private Vehicle vehicle;
    
    private User user;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VehicleResource vehicleResource = new VehicleResource();
        ReflectionTestUtils.setField(vehicleResource, "carSalesService", carSalesService);
        this.restAdMockMvc = MockMvcBuilders.standaloneSetup(vehicleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
    	user = new User();
    	user.setId(OWNER_ID);
    	user.setEmail(OWNER_EMAIL);
    	user.setName(OWNER_NAME);
    	user.setPassword(OWNER_PW);
    	user.setPhone(OWNER_CONTACT_NUMBER);
    	user.setAddress(OWNER_ADDRESS);

    	vehicle = new Vehicle();
    	vehicle.setReg(VEHICLE_REG);
    	vehicle.setYear(VEHICLE_YEAR);
    	vehicle.setMilage(VEHICLE_MILAGE);
        vehicle.setColour(VEHICLE_COLOUR);
        vehicle.setMake(VEHICLE_MAKE);
        vehicle.setModel(VEHICLE_MODEL);
    }
    
    @Test
    @Transactional
    public void testGetAllVehicles() throws Exception {
    	int ownerId = carSalesService.createUser(user);
    	vehicle.setOwner(ownerId);
    	carSalesService.createVehicle(vehicle);

    	restAdMockMvc.perform(get("/api/cars"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
         .andExpect(jsonPath("$.[*].reg").value(hasItem(VEHICLE_REG)))
         .andExpect(jsonPath("$.[?(@.reg=="+VEHICLE_REG+")].year").value(hasItem(VEHICLE_YEAR)))
         .andExpect(jsonPath("$.[?(@.reg=="+VEHICLE_REG+")].milage").value(hasItem(VEHICLE_MILAGE)))
         .andExpect(jsonPath("$.[?(@.reg=="+VEHICLE_REG+")].colour").value(hasItem(VEHICLE_COLOUR)))
         .andExpect(jsonPath("$.[?(@.reg=="+VEHICLE_REG+")].make").value(hasItem(VEHICLE_MAKE)))
         .andExpect(jsonPath("$.[?(@.reg=="+VEHICLE_REG+")].model").value(hasItem(VEHICLE_MODEL)))
         .andExpect(jsonPath("$.[?(@.reg=="+VEHICLE_REG+")].owner").value(hasItem(ownerId)));

    }
    
    @Test
    @Transactional
    public void testCreateVehicle() throws Exception {
        int databaseSizeBeforeCreate = carSalesService.getAllVehicles().size();

        int ownerId = carSalesService.createUser(user);
    	vehicle.setOwner(ownerId);
    	
        // Create the Booking
        restAdMockMvc.perform(post("/api/cars")
                .contentType(APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vehicle)))
                .andExpect(status().isCreated());

        // Validate the Vehicle in the database
        List<Vehicle> vehicles = carSalesService.getAllVehicles();
        assertThat(vehicles).hasSize(databaseSizeBeforeCreate + 1);
        //TODO improve this to test the exact vehicle was saved
    }

    
}
