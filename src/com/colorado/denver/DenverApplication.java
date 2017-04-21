package com.colorado.denver;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.colorado.denver.services.UserService;
import com.colorado.denver.services.persistence.SessionTools;
import com.colorado.denver.tools.GenericTools;
import com.colorado.denver.tools.Tools;
import com.google.gson.GsonBuilder;

import ch.qos.logback.classic.Level;

/*
 * Keep this class clean! only main method and temporary experiments!
 */

@EnableWebMvc
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@Configuration
@ComponentScan("com.colorado")
public class DenverApplication extends SpringBootServletInitializer {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DenverApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Starting app!");

		// DO NOT DELETE THESE LINES OF CODE I DARE YOU!
		try {
			ApplicationContext ctx = SpringApplication.run(DenverApplication.class, args);
			// True = Use the update routine
			// If you want to rebuild the DB with CREATE use DenverDBSetupTest.java
			SessionTools.createSessionFactory(true);
			GenericTools gt = new GenericTools();
			gt.setApplicationContext(ctx);
			UserService.authorizeSystemuser();
			Tools.setLoggingLevel(Level.ERROR);
			LOGGER.info("Successfully obtained system user Token. DB and security should be in healthy state");

		} catch (Exception e) {
			LOGGER.error("Error during starting up the app! Check the DB!");
			e.printStackTrace();
			SpringApplication.exit(GenericTools.getApplicationContext());
		}
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DenverApplication.class);
	}

	public static String toJson(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.create();
		return json;
	}

	// useless shit?
	// @Bean
	// public WebMvcConfigurer corsConfigurer() {
	// return new WebMvcConfigurerAdapter() {
	// @Override
	// public void addCorsMappings(CorsRegistry registry) {
	// registry.addMapping("/**").allowedOrigins("http://localhost:8080", "http://localhost:8081");
	// }
	// };
	// }

}