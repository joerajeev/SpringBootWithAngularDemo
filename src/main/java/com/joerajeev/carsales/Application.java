package com.joerajeev.carsales;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jboss.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application {

	private static Logger log = Logger.getLogger(Application.class);
	
    public static void main(String[] args) throws Exception {
    	log.info("Starting Application");
    	SpringApplication.run(Application.class, args);
    }

	@Bean(destroyMethod = "close")
	public DataSource getDataSource(Environment env) {
		BasicDataSource dataSource = new BasicDataSource();
			dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
			dataSource.setUsername(env.getRequiredProperty("db.user"));
			dataSource.setPassword(env.getRequiredProperty("db.password"));
			dataSource.setUrl(env.getRequiredProperty("db.url"));
			return dataSource;
	}
	

}