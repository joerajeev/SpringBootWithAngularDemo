package com.joerajeev.carsales.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.List;

import javax.annotation.PostConstruct;

import org.aspectj.weaver.AjAttribute.MethodDeclarationLineNumberAttribute;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.joerajeev.carsales.Application;
import com.joerajeev.carsales.entity.Authority;
import com.joerajeev.carsales.entity.User;
import com.joerajeev.carsales.entity.Vehicle;
import com.joerajeev.carsales.repository.AuthorityRepository;
import com.joerajeev.carsales.repository.UserRepository;
import com.joerajeev.carsales.repository.VehicleRepository;

/**
 * Test class for the VechileResource REST controller.
 *
 * @see VechicleResource
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
	private static final Integer VEHICLE_YEAR = 2014;
	private static final String OWNER_ADDRESS = "Mooroolbark";
	private static final String OWNER_CONTACT_NUMBER = "123456789";
	private static final String OWNER_PW = "asdfasdf";
	private static final String OWNER_NAME = "Joseph";
	private static final String OWNER_EMAIL = "joerajeev@gmail.com";
	private static final Integer OWNER_ID = 100;
	private static final String VEHICLE_REG = "REGO101";
    private static final Integer VEHICLE_MILAGE = 10000;
	private static final String VEHICLE_MODEL = "Civic";
	private static final String VEHICLE_MAKE = "Honda";
	private static final String VEHICLE_COLOUR = "Black";

	@Autowired
	private VehicleRepository vehicleRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AuthorityRepository authorityRepo;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVechileMockMvc;

    private Authority authority;
    private User user;
    private Vehicle vehicle;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VehicleResource vehicleResource = new VehicleResource();
        ReflectionTestUtils.setField(vehicleResource, "vehicleRepo", vehicleRepo);
        this.restVechileMockMvc = MockMvcBuilders.standaloneSetup(vehicleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
    	authority = new Authority();
    	authority.setAuthority("ROLE_USER");
    	authority.setUsername(OWNER_EMAIL);
    	
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
        vehicle.setUser(user);
        
        authorityRepo.save(authority);
    	user = userRepo.save(user);

    }
    
    @Test
    @Transactional
    public void testGetAllVehicles() throws Exception {
    	    	
    	vehicleRepo.save(vehicle);

    	restVechileMockMvc.perform(get("/api/cars"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
         .andExpect(jsonPath("$.[*].reg").value(hasItem(VEHICLE_REG)))
         .andExpect(vehicleAttributeToContainValue("year", VEHICLE_YEAR))
         .andExpect(vehicleAttributeToContainValue("milage", VEHICLE_MILAGE))
         .andExpect(vehicleAttributeToContainValue("colour", VEHICLE_COLOUR))
         .andExpect(vehicleAttributeToContainValue("make", VEHICLE_MAKE))
         .andExpect(vehicleAttributeToContainValue("model", VEHICLE_MODEL))
         .andExpect(vehicleAttributeToContainValue("user.id", user.getId()));
    	
    }

	@SuppressWarnings("unchecked")
	protected <V extends Object> ResultMatcher vehicleAttributeToContainValue(String attribute, V value) {
		return jsonPath("$.[?(@.reg=="+VEHICLE_REG+")]."+ attribute).value(contains(value));
	}
    
    @Test
    @Transactional
    public void testCreateVehicle() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepo.findAll().size();

        // Create the Booking
        restVechileMockMvc.perform(post("/api/cars")
                .contentType(APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(vehicle)))
                .andExpect(status().isCreated());

        // Validate the Vehicle in the database
        List<Vehicle> vehicles = vehicleRepo.findAll();
        assertThat(vehicles).hasSize(databaseSizeBeforeCreate + 1);
       // vehicleRepo.findOne()
        //TODO improve this to test the exact vehicle was saved
    }

   /* @Test
    public void testFindVehicle() throws Exception{
    	
    	vehicleRepo.save(vehicle);
    	
    	restVechileMockMvc.perform(get("/api/cars/{reg}", vehicle.getReg()))
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    		.andExpect(jasonpath(""))
    }*/
    
}
