package com.joerajeev.carsales.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.joerajeev.carsales.service.Vehicle;

/**
 * Data access class for the Cars object
 * 
 * @author Rajeev
 *
 */
@Component("carsDao")
public class CarsDao {

	Logger logger = Logger.getLogger(CarsDao.class.getName());
	
	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<Vehicle> getAllVehicles(){
		
		String selectQuery = "select reg, year, owner, milage, colour, make, model from vehicle";
		return jdbc.query(selectQuery, (ResultSet rs, int rowNum) -> {
				Vehicle vehicle = new Vehicle();
				vehicle.setReg(rs.getString(1));
				vehicle.setYear(rs.getInt(2));
				vehicle.setOwner(rs.getInt(3));
				vehicle.setMilage(rs.getInt(4));
				vehicle.setColour(rs.getString(5));
				vehicle.setMake(rs.getString(6));
				vehicle.setModel(rs.getString(7));
				return vehicle;
			
		});
	}
	
	/**
	 * Creates a car.
	 * Demonstrates BeanPropertyParameterSource.
	 * 
	 * @param car
	 * @return
	 * @throws Exception
	 */
	public boolean create(Vehicle car) throws Exception {
		
		String insertSql = "insert into vehicle (reg, year, owner, milage, colour, make, model) "
				+ "values (:reg, :year, :owner, :milage, :colour, :make, :model)"; 

			BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(car);
			jdbc.update(insertSql, source);
			return true;
	}
}
