package com.colorado.denver.services.persistance;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.LoggerFactory;

import com.colorado.denver.DenverApplication;

public class SessionTools {
	
	public static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SessionTools.class);
	

	
	public static void createSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			configuration.configure();
			if(configuration.getProperty("hibernate.hbm2ddl.auto").equals("create")){
				LOGGER.info("");
				LOGGER.info("======================================================");
				LOGGER.info("");
				LOGGER.info("HIBERNATE >CREATE< MODE! ALL DATA WILL BE DROPPED!!!!!");
				LOGGER.info("");
				LOGGER.info("======================================================");
				LOGGER.info("");
				}
			serviceRegistry = new ServiceRegistryBuilder().applySettings(
					configuration.getProperties()).buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			LOGGER.error("Failed to create sessionFactory" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
}
