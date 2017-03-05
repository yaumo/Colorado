package com.colorado.denver;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.impl.Log4JLogger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.controller.HomeController;
import com.colorado.denver.model.Home;
import com.colorado.denver.services.javabytecoder.Executor;

@EnableWebMvc
@SpringBootApplication
public class DenverApplication {
	public static SessionFactory factory;
	private static ServiceRegistry serviceRegistry;
	private static final Log4JLogger LOGGER = new Log4JLogger();

	public static void main(String[] args) throws IOException {
		System.out.println("Running app!");
		// Use log4j
		SpringApplication.run(DenverApplication.class, args);

		System.out.println("DOING SOME CRAZY SHIT FUCK THIS BULLSHIT FUCK");
		String filePath = new File("").getAbsolutePath();
		System.out.println("The path is:" + filePath + "\\Test.class");
		Executor.addFile(filePath + "\\Test.class");
		System.out.println("I DID SOME CRAZY SHIT AND IT DIDN'T CRASH");
		createSessionFactory();
		
		//			    					//
		//		   Hibernate Usage			//
		//				    				//
		HibernateController hibCtrl = new HibernateController();
		/* Add few home records in database */		
		Home home1 = new Home("Home1","content1");
		hibCtrl.addEntity(home1);
		hibCtrl.addEntity(new Home("Home2","content2"));
		
		//Entity Update Example
		home1.setContent("updated3");
		home1.setAnotherContent("anotherUpdate1");
		hibCtrl.updateEntity(home1);
		home1.setContent("stillTheSame?");
		hibCtrl.deleteEntity(home1);
		
		//get Entity from table
		Home returnedHome = (Home)hibCtrl.getEntity("home00002", Home.class);
		System.out.println(returnedHome.getAnotherContent());
		
		//get List of Entities from table
		List<Home> homes = (List<Home>)(List<?>)hibCtrl.getEntityList(Home.class);//if(weFail){system.crashAndBurn();}
		 for (Iterator iterator = 
        		 homes.iterator(); iterator.hasNext();){
            Home home = (Home) iterator.next(); 
            System.out.println("ID: " + home.getId()); 
            System.out.println("Content: " + home.getContent()); 
         }
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