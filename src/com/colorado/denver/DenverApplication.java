package com.colorado.denver;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.commons.logging.impl.SLF4JLog;
import org.apache.commons.logging.impl.SLF4JLogFactory;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.model.Home;

@EnableWebMvc
@SpringBootApplication
public class DenverApplication {
	public static SessionFactory factory;
	private static ServiceRegistry serviceRegistry;
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DenverApplication.class);
	
	
	public static void main(String[] args) throws IOException {
		LOGGER.info("Starting app!");
		SpringApplication.run(DenverApplication.class, args);

		System.out.println("Creating session factory..");
		try {
			createSessionFactory();
		} catch (Exception e) {
			LOGGER.error("DB Setup and / or connection failed!");
			e.printStackTrace();
			SpringApplication.exit(null, null);
		}

		LOGGER.info("Done Creating session factory.");
		// //
		// Hibernate Usage //
		// //
		LOGGER.info("Starting with Hibernate experiments..");
		HibernateController hibCtrl = new HibernateController();
		/* Add few home records in database */
		Home home1 = new Home("Home1", "content1");
		hibCtrl.addEntity(home1);
		hibCtrl.addEntity(new Home("Home2", "content2"));

		Exercise exc = new Exercise("Exercise1","Des of exercise1");
		exc.setTitle("Hello i should have overriden exervise 1");
		String ecxId = hibCtrl.addEntity(exc);

		// Entity Update Example
		home1.setContent("updated3");
		home1.setAnotherContent("anotherUpdate1");
		hibCtrl.updateEntity(home1);
		home1.setContent("stillTheSame?");
		hibCtrl.deleteEntity(home1);

		// get Entity from table
		Home returnedHome = (Home) hibCtrl.getEntity("home00002", Home.class);
		System.out.println(returnedHome.getAnotherContent());

		Exercise returnedExcercise = (Exercise) hibCtrl.getEntity(ecxId, Exercise.class);
		LOGGER.error("The title of excercise is: " + returnedExcercise.getTitle());
		LOGGER.error("The is of excercise is: " + returnedExcercise.getId());

		// get List of Entities from table
		List<Home> homes = (List<Home>) (List<?>) hibCtrl.getEntityList(Home.class);// if(weFail){system.crashAndBurn();}
		for (Iterator iterator = homes.iterator(); iterator.hasNext();) {
			Home home = (Home) iterator.next();
			LOGGER.info("ID: " + home.getId());
			LOGGER.info("Content: " + home.getContent());
			LOGGER.info("ObjectClass: " + home.getObjectClass());
		}
		LOGGER.info("--------------END OF HIBERNATE EXPERIMENTS------------------");
	}

	public static void createSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			configuration.configure();
			serviceRegistry = new ServiceRegistryBuilder().applySettings(
					configuration.getProperties()).buildServiceRegistry();
			factory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

}