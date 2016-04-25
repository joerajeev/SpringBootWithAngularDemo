package com.joerajeev.carsales.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.joerajeev.carsales.Application;
import com.joerajeev.carsales.entity.Advert;
import com.joerajeev.carsales.entity.Authority;
import com.joerajeev.carsales.entity.User;
import com.joerajeev.carsales.entity.Vehicle;
import com.joerajeev.carsales.repository.AdvertRepository;
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
public class AdvertResourceIntTest {
	
	private static final Date ADVERT_CREATED = new Date();
	private static final String ADVERT_DESCRIPTION = "An awesome newish car";
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
	private AdvertRepository adRepo;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAdvertMockMvc;

    private Authority authority;
    private User user;
    private Vehicle vehicle;
    private Advert ad;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AdvertResource advertResource = new AdvertResource();
        ReflectionTestUtils.setField(advertResource, "advertRepo", adRepo);
        ReflectionTestUtils.setField(advertResource, "vehicleRepo", vehicleRepo);
        this.restAdvertMockMvc = MockMvcBuilders.standaloneSetup(advertResource)
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
        
        ad = new Advert();
        ad.setCreated(ADVERT_CREATED);
        ad.setDescription(ADVERT_DESCRIPTION);
        ad.setVehicle(vehicle);
        
        authorityRepo.saveAndFlush(authority);
    	user = userRepo.saveAndFlush(user);
    	vehicleRepo.saveAndFlush(vehicle);

    }
    
    @Test
    @Transactional
    public void testGetAllAds() throws Exception {
    	    	
    	ad = adRepo.saveAndFlush(ad);

    	restAdvertMockMvc.perform(get("/api/ads"))
         .andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
         .andExpect(jsonPath("$.[*].id").value(hasItem(ad.getId())))
         .andExpect(advertAttributeToContainValue("description", ADVERT_DESCRIPTION))
         .andExpect(advertAttributeToContainValue("created", ADVERT_CREATED.getTime()))
         .andExpect(advertAttributeToContainValue("vehicle.reg", VEHICLE_REG));
    	
    }

	@SuppressWarnings("unchecked")
	protected <V extends Object> ResultMatcher advertAttributeToContainValue(String attribute, V value) {
		return jsonPath("$.[?(@.id=="+ad.getId()+")]."+ attribute).value(contains(value));
	}
    
    @Test
    @Transactional
    public void testCreateAdvert() throws Exception {
        int databaseSizeBeforeCreate = adRepo.findAll().size();

        // Create the Vehicle
        restAdvertMockMvc.perform(post("/api/ads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ad)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.description").value(ADVERT_DESCRIPTION))
                .andExpect(jsonPath("$.created").value(ADVERT_CREATED.getTime()))
                .andExpect(jsonPath("$.image").value(nullValue()))
                .andExpect(jsonPath("$.modified").value(nullValue()))
                .andExpect(jsonPath("$.vehicle.reg").value(VEHICLE_REG));

        // Validate the Vehicle in the database
        List<Advert> allAds = adRepo.findAll();
        assertThat(allAds).hasSize(databaseSizeBeforeCreate + 1);
    }

    @Test
    @Transactional
    public void testFindAd() {
    	
    	ad = adRepo.saveAndFlush(ad);
    	
    	try {
			restAdvertMockMvc.perform(get("/api/ads/{id}", ad.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(ad.getId()))
				 .andExpect(jsonPath("$.description").value(ADVERT_DESCRIPTION))
	             .andExpect(jsonPath("$.created").value(ADVERT_CREATED.getTime()))
	             .andExpect(jsonPath("$.image").value(nullValue()))
	             .andExpect(jsonPath("$.modified").value(nullValue()))
	             .andExpect(jsonPath("$.vehicle.reg").value(VEHICLE_REG));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
