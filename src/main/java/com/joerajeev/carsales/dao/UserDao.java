package com.joerajeev.carsales.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.joerajeev.carsales.service.User;

/**
 * Data Access class for the User object
 * 
 * @author Rajeev
 *
 */
@Component("userDao")
public class UserDao {

	Logger logger = Logger.getLogger(UserDao.class.getName());
	
	private NamedParameterJdbcTemplate jdbc;
	
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	/**
	 * Creates a user.
	 * Demonstrates MapSqlParameterSource.
	 * 
	 * @param user
	 * @return id of the user created
	 * @throws DataAccessException
	 */
	public int create(User user) throws DataAccessException {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", user.getName());
		params.addValue("phone", user.getPhone());
		params.addValue("address", user.getAddress());
		params.addValue("email", user.getEmail());
		params.addValue("password", user.getPassword());
		params.addValue("enabled", 1);
		
		String insertSql = "insert into users(name, phone, address, email, password, enabled) "
				+ "values (:name , :phone, :address, :email, :password, :enabled)"; 

			//BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(user);
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbc.update(insertSql, params, keyHolder);
			return keyHolder.getKey().intValue();
		
	}
	
	public User read(String username) {
		String sql = "select * from users where email = :email";
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", username);
		return (User)jdbc.queryForObject(sql, map, new BeanPropertyRowMapper<User>(User.class));
	}
}
