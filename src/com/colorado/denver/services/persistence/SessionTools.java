package com.colorado.denver.services.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.LoggerFactory;

public class SessionTools {

	public static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SessionTools.class);

	public static void createSessionFactory(boolean useHibernateConfigUpdateRoutine) {
		try {
			// Create configuration instance
			Configuration configuration = new Configuration();

			// Pass hibernate configuration file
			configuration.configure("hibernate.cfg.xml");

			// Since version 4.x, service registry is being used
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

			// Create session factory instance
			SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);

			// Get current session
			Session session = factory.getCurrentSession();

			// Begin transaction
			session.getTransaction().begin();

			// Print out all required information
			System.out.println("Session Is Opened :: " + session.isOpen());
			System.out.println("Session Is Connected :: " + session.isConnected());

			// Commit transaction
			session.getTransaction().commit();

			System.exit(0);

			/*
			 * configuration.configure();
			 * if (useHibernateConfigUpdateRoutine) {
			 * if (configuration.getProperty("hibernate.hbm2ddl.auto").equals("create")) {
			 * LOGGER.info("");
			 * LOGGER.info("======================================================");
			 * LOGGER.info("");
			 * LOGGER.info("HIBERNATE >CREATE< MODE! ALL DATA WILL BE DROPPED!!!!!");
			 * LOGGER.info("");
			 * LOGGER.info("======================================================");
			 * LOGGER.info("");
			 * }
			 * } else {
			 * configuration.setProperty("hibernate.hbm2ddl.auto", "update");
			 * LOGGER.info("");
			 * LOGGER.info("======================================================");
			 * LOGGER.info("");
			 * LOGGER.info("          HIBERNATE >UPDATE< MODE!");
			 * LOGGER.info("");
			 * LOGGER.info("======================================================");
			 * LOGGER.info("");
			 * }
			 * serviceRegistry = new ServiceRegistryBuilder().applySettings(
			 * configuration.getProperties()).buildServiceRegistry();
			 * sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			 * 
			 */
		} catch (

		Throwable ex) {
			LOGGER.error("Failed to create sessionFactory" + ex);
			throw new ExceptionInInitializerError(ex);
		}

	}

}
