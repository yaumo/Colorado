package com.colorado.denver.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.colorado.denver.DenverApplication;
import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.DenverUser;
import com.colorado.denver.services.persistance.HibernateGeneralTools;
import com.colorado.denver.services.persistance.SessionTools;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DenverDBSetupTest {
	
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DenverDBSetupTest.class);
	
	@Test
	public void setupDatabase(){
		//Using hibernate config file!
		LOGGER.info("Creating session factory..");
			SessionTools.createSessionFactory();
		LOGGER.info("Done Creating session factory.");
		
		//Creating system user
		HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();
		DenverUser usr = new DenverUser();
		
		
		
		hibCtrl.addEntity(usr);
	}
}
