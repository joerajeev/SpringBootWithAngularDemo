package com.joerajeev.carsales.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joerajeev.carsales.dao.AuthorityDao;
import com.joerajeev.carsales.dao.CarsDao;
import com.joerajeev.carsales.dao.UserDao;

/**
 * Service level class providing car advert related services
 * 
 * @author Rajeev
 *
 */
@Service("adService")
public class CarSalesService {

	Logger logger = Logger.getLogger(CarSalesService.class.toString());
	
	@Autowired
	private CarsDao carsDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AuthorityDao authorityDao;
	
	public List<Vehicle> getAllVehicles(){
		return carsDao.getAllVehicles();
	}
	
	public boolean createVehicle(Vehicle vehicle) throws ServiceException{
		try {
			return carsDao.create(vehicle);
		} catch (Exception e) {
			logger.warning("Error creating vehicle.");
			throw new ServiceException("Error creating vehicle", e);
		}
	}
	
	/**
	 * 
	 * @param user
	 * @return id of the user created
	 */
	public int createUser(User user) throws ServiceException{
		try {
			authorityDao.create(user.getEmail(), "ROLE_USER");
			return userDao.create(user);
		} catch (Exception e) {
			logger.warning("Error creating user "+e);
			throw new ServiceException("Error creating user", e);
		}
	}
	
	public User readByUsername(String username) {
		
		return userDao.read(username);
	}
	
	public void setCarsDao(CarsDao carsDao) {
		this.carsDao = carsDao;
	}
	
	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
