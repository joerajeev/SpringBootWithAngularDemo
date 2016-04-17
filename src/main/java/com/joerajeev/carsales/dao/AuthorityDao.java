package com.joerajeev.carsales.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Data access class for the authority data 
 * 
 * @author Rajeev
 *
 */
@Component("authorityDao")
public class AuthorityDao {
	
	private JdbcTemplate jdbc;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbc = new JdbcTemplate(dataSource);
	}
	
	/**
	 * Creates an authority.
	 * Demonstrates basic insert without parameter sources's.
	 * 
	 * @param email
	 * @param authority
	 * @return
	 * @throws Exception
	 */
	public boolean create(String email, String authority) throws Exception{
		return (jdbc.update("insert into authorities (username,authority) values (?,?)", new Object[]{email, authority}) == 1);
	}

}
