package com.colorado.denver.services.persistence;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.LoggerFactory;

import com.colorado.denver.model.Course;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.model.Home;
import com.colorado.denver.model.Lecture;
import com.colorado.denver.model.Role;
import com.colorado.denver.model.Solution;
import com.colorado.denver.model.User;

public class SessionTools {

	public static SessionFactory sessionFactory;// SINGLETON!
	private static ServiceRegistry serviceRegistry;
	public static Session session;

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SessionTools.class);

	public static void createSessionFactory(boolean useUpdateRoutine) {
		try {
			// Create configuration instance
			Configuration configuration = new Configuration();

			// Pass hibernate configuration file
			configuration.configure("hibernate.cfg.xml");

			Properties prop = new Properties();
			if (useUpdateRoutine) {
				prop.setProperty("hibernate.hbm2ddl.auto", "update");
			} else {
				prop.setProperty("hibernate.hbm2ddl.auto", "create");
			}
			configuration.addProperties(prop);
			LOGGER.info("Try using UpdateRoutine: " + useUpdateRoutine);
			LOGGER.info("Hibernate mode: " + configuration.getProperty("hibernate.hbm2ddl.auto"));
			// Since version 4.x, service registry is being used
			serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			LOGGER.info("Creating session factory instance!");

			// Create session factory instance
			sessionFactory = configuration.addPackage("com.colorado").addProperties(prop).addAnnotatedClass(Role.class)
					.addAnnotatedClass(User.class).addAnnotatedClass(Home.class).addAnnotatedClass(Exercise.class)
					.addAnnotatedClass(Lecture.class).addAnnotatedClass(Course.class).addAnnotatedClass(Solution.class)
					.buildSessionFactory(serviceRegistry);

			// TODO: Drop sequences!

			LOGGER.info("Created session factory instance!");
			// Get current session
			session = sessionFactory.getCurrentSession();
			// Begin transaction
			session.getTransaction().begin();

			// Print out all required information
			System.out.println("Session Is Opened :: " + session.isOpen());
			System.out.println("Session Is Connected :: " + session.isConnected());

			// Commit transaction
			session.getTransaction().commit();
			LOGGER.info("Session opened and committed!!");
			/*
			 * configuration.configure(); if (useHibernateConfigUpdateRoutine) {
			 * if (configuration.getProperty("hibernate.hbm2ddl.auto").equals(
			 * "create")) { LOGGER.info(""); LOGGER.info(
			 * "======================================================");
			 * LOGGER.info(""); LOGGER.
			 * info("HIBERNATE >CREATE< MODE! ALL DATA WILL BE DROPPED!!!!!");
			 * LOGGER.info(""); LOGGER.info(
			 * "======================================================");
			 * LOGGER.info(""); } } else {
			 * configuration.setProperty("hibernate.hbm2ddl.auto", "update");
			 * LOGGER.info(""); LOGGER.info(
			 * "======================================================");
			 * LOGGER.info("");
			 * LOGGER.info("          HIBERNATE >UPDATE< MODE!");
			 * LOGGER.info(""); LOGGER.info(
			 * "======================================================");
			 * LOGGER.info(""); } serviceRegistry = new
			 * ServiceRegistryBuilder().applySettings(
			 * configuration.getProperties()).buildServiceRegistry();
			 * sessionFactory =
			 * configuration.buildSessionFactory(serviceRegistry);
			 * 
			 */
		} catch (

		Throwable ex) {
			LOGGER.error("Failed to create sessionFactory" + ex);
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}

	}

}
