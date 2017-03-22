package com.colorado.denver.services.persistance;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.LoggerFactory;

public class SessionTools {
	
	public static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SessionTools.class);
	

	public static void createSessionFactory(boolean useHibernateConfigUpdateRoutine) {
		try {
			Configuration configuration = new Configuration();
			configuration.configure();
			if(useHibernateConfigUpdateRoutine){
				if(configuration.getProperty("hibernate.hbm2ddl.auto").equals("create")){
				LOGGER.info("");
				LOGGER.info("======================================================");
				LOGGER.info("");
				LOGGER.info("HIBERNATE >CREATE< MODE! ALL DATA WILL BE DROPPED!!!!!");
				LOGGER.info("");
				LOGGER.info("======================================================");
				LOGGER.info("");
				}
			}else{
				configuration.setProperty("hibernate.hbm2ddl.auto", "update");
				LOGGER.info("");
				LOGGER.info("======================================================");
				LOGGER.info("");
				LOGGER.info("          HIBERNATE >UPDATE< MODE!");
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
