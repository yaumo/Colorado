package com.colorado.denver.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Role;
import com.colorado.denver.model.User;
import com.colorado.denver.services.persistence.HibernateGeneralTools;
import com.colorado.denver.services.persistence.SessionTools;
import com.colorado.denver.tools.DenverConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DenverDBSetupTest {
	private SessionFactory sessionFactory;
	private Session session = null;

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DenverDBSetupTest.class);

	@Before
	public void before() {
		// Using hibernate config file!

		LOGGER.info("Creating session factory..");
		SessionTools.createSessionFactory(true);
		LOGGER.info("Done Creating session factory.");
	}

	@Test
	public void setupDatabase() {

		createRole("ROLE_ADMIN");
		createRole("ROLE_USER");
		createSystemUser();
	}

	@After
	public void after() {
		session.close();
		sessionFactory.close();
	}

	private Role createRole(String name) {
		LOGGER.info(
				"Creating new Role in Database CREATE! If you try to create roles during 'update' YOU'LL FUCK EVERYTHING UP");
		LOGGER.info("Database Update mode not supported. (DIY)");
		Role role = new Role();
		role.setRoleName(name);
		HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();
		hibCtrl.addEntity(role);
		hibCtrl.addEntity(role);

		return role;
	}

	private static void createSystemUser() { // Creating system user

		User systemUser = new User();
		systemUser.setUsername(DenverConstants.SYSTEM);
		systemUser.setPassword("password");
		systemUser.setCreator(systemUser);//
		// assignToSelf. Bypasses generic Creator assignment
		LOGGER.info("Created System User");

		Role systemRole = new Role();
		systemRole.setRoleName("ROLE_ADMIN");
		systemRole.setUser(systemUser);
		LOGGER.info("Created Role:" + systemRole.getRoleName()); // systemUser.getRoles().add(systemRole);

		// Hib save HibernateController hibCtrl =
		HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();
		LOGGER.info("Sucessful controler obtained");
		hibCtrl.addEntity(systemUser);
		LOGGER.info("Sucessful systemuser Save(Database)");

	}

}
