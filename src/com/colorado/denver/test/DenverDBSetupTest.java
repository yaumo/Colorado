package com.colorado.denver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.controller.entityController.RoleController;
import com.colorado.denver.model.Role;
import com.colorado.denver.model.User;
import com.colorado.denver.services.UserService;
import com.colorado.denver.services.persistence.HibernateGeneralTools;
import com.colorado.denver.services.persistence.SessionTools;
import com.colorado.denver.tools.DenverConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DenverDBSetupTest {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DenverDBSetupTest.class);

	@Before
	public void before() {
		// Using hibernate config file!
		boolean useUpdate = false;
		LOGGER.info("Creating session factory..");
		SessionTools.createSessionFactory(useUpdate);
		LOGGER.info("Done Creating session factory.");

	}

	@Test
	public void setupDatabase() {

		Role roleAdmin = createRole(UserService.ROLE_GLOBAL_ADMINISTRATOR);
		Role roleUser = createRole(UserService.ROLE_USER);
		User systemUser = createSystemUser();

		updateRoles(roleAdmin, roleUser, systemUser);
	}

	@After
	public void after() {
		SessionTools.sessionFactory.close();

	}

	private boolean updateRoles(Role roleAdmin, Role roleUser, User systemUser) {
		Set<User> systemUsers = new HashSet<>();
		systemUsers.add(systemUser);
		roleAdmin.setUsers(systemUsers);
		roleUser.setUsers(systemUsers);

		try {
			HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();
			hibCtrl.mergeEntity(roleAdmin);
			hibCtrl.mergeEntity(roleUser);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private Role createRole(String name) {
		LOGGER.info(
				"Creating new Role in Database CREATE! If you try to create roles during 'update' YOU'LL FUCK EVERYTHING UP");
		LOGGER.info("Database Update mode not supported. (DIY)");
		Role role = new Role();
		role.setRoleName(name);
		HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();
		hibCtrl.addEntity(role);

		return role;
	}

	private static User createSystemUser() { // Creating system user

		User systemUser = new User();
		systemUser.setUsername(DenverConstants.SYSTEM);
		systemUser.setPassword("password");
		LOGGER.info("Created System User");

		// Hib save HibernateController hibCtrl =
		HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();

		Role systemRole = RoleController.getRoleByName(UserService.ROLE_GLOBAL_ADMINISTRATOR);

		LOGGER.info("Got Role:" + systemRole.getRoleName());
		ArrayList<Role> systemRoles = new ArrayList<Role>(0);
		Set<User> systemUsers = new HashSet<User>();
		systemUsers.add(systemUser);
		systemRole.setUsers(systemUsers);

		systemRoles.add(systemRole);
		systemUser.setRoles(systemRoles);

		hibCtrl.addEntity(systemUser);
		hibCtrl.updateEntity(systemRole);
		LOGGER.info("Sucessful systemuser Save(Database)");
		UsernamePasswordAuthenticationToken returnedToken = UserService.authorizeSystemuser();
		User returnedU = UserService.getUserByLoginName(returnedToken.getPrincipal().toString());

		// Make sure authorization worked
		assertNotNull("Authorizitation of system user faield!", returnedU);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		assertNotNull("Authentication is Null!", auth);
		assertEquals(returnedU.getUsername(), UserService.getLoginNameFromAuthentication(auth));
		return returnedU;

	}

}
