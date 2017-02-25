package com.colorado.denver;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.colorado.denver.controller.HomeController; 

@EnableWebMvc
@SpringBootApplication
public class DenverApplication {
	public static SessionFactory factory; 
	private static ServiceRegistry serviceRegistry;
	
	public static void main(String[] args) {
		System.out.println("Running app!");
		// Use log4j
		SpringApplication.run(DenverApplication.class, args);
		
		createSessionFactory();
		
		
		/* Add few home records in database */
		  HomeController homectrl = new HomeController();
		  homectrl.addHome("Home1");
		  homectrl.addHome("Home2");
		  homectrl.addHome("Home3");

	      /* List down all the homes */
		  homectrl.listHomes();
	}
	
	public static void createSessionFactory() {
		try{
	    Configuration configuration = new Configuration();
	    configuration.configure();
	    serviceRegistry = new ServiceRegistryBuilder().applySettings(
	            configuration.getProperties()). buildServiceRegistry();
	    factory = configuration.buildSessionFactory(serviceRegistry);
		}catch (Throwable ex) { 
	        System.err.println("Failed to create sessionFactory object." + ex);
	        throw new ExceptionInInitializerError(ex); 
	     }
	}

}