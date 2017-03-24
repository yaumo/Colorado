package com.colorado.denver;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.model.Home;
import com.colorado.denver.services.UserService;
import com.colorado.denver.services.persistence.SessionTools;

/*
 * Keep this class clean! only main method and temporary experiments!
 */

@EnableWebMvc
@SpringBootApplication
// @EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class })
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class DenverApplication extends SpringBootServletInitializer {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DenverApplication.class);

	public static void main(String[] args) throws IOException {
		LOGGER.info("Starting app!");
		SpringApplication.run(DenverApplication.class, args);

		// //
		// Hibernate Usage //
		// //
		// True = Use the update routine
		// If you want to rebuild the DB with CREATE use DenverDBSetupTest.java
		SessionTools.createSessionFactory(true);

		LOGGER.info("Starting with Hibernate experiments..");
		HibernateController hibCtrl = new HibernateController();
		/* Add few home records in database */

		UserService.authorizeSystemuser();
		Home home1 = new Home();
		home1.setContent("Home1");
		hibCtrl.addEntity(home1);
		Home home2 = new Home();
		home1.setContent("Home2");
		hibCtrl.addEntity(home2);
		home1.setContent("Home3");
		Home home3 = new Home();
		hibCtrl.addEntity(home3);

		// Delete test
		hibCtrl.deleteEntity(home2);

		home3.setContent("Home3MODIFIED");
		hibCtrl.updateEntity(home3);

		Exercise exc = new Exercise();
		exc.setTitle("Hello i should have overriden exervise 1");
		String ecxId = hibCtrl.addEntity(exc);

		Exercise returnedExcercise = (Exercise) hibCtrl.getEntity(ecxId, Exercise.class);
		LOGGER.info("The title of excercise is: " + returnedExcercise.getTitle());
		LOGGER.info("The id of excercise is: " + returnedExcercise.getId());
		LOGGER.info("The creator of excercise is: " + returnedExcercise.getCreator().getUsername());
		LOGGER.info("The ObjectClass Of exercise is: " + returnedExcercise.getObjectClass());

		// get List of Entities from table
		List<Home> homes = (List<Home>) (List<?>) hibCtrl.getEntityList(Home.class);// if(weFail){system.crashAndBurn();}
		for (Iterator iterator = homes.iterator(); iterator.hasNext();) {
			Home home = (Home) iterator.next();
			LOGGER.info("ID: " + home.getId());
			LOGGER.info("Content: " + home.getContent());
			LOGGER.info("Creator: " + home.getCreator().getUsername());
			LOGGER.info("ObjectClass: " + home.getObjectClass());
		}
		LOGGER.info("--------------END OF HIBERNATE EXPERIMENTS------------------");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DenverApplication.class);
	}

}