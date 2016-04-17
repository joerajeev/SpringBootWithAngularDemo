package com.joerajeev.carsales;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jboss.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private static Logger log = Logger.getLogger(Application.class);
	
    public static void main(String[] args) throws Exception {
    	log.info("Starting Application");
    	SpringApplication.run(Application.class, args);
    }

	@Bean
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setUsername("javauser");
			dataSource.setPassword("javadude");
			dataSource.setUrl("jdbc:mysql://localhost:3306/carsales");
			return dataSource;
	}
	

}