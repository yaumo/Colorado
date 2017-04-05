package com.colorado.denver;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.colorado.denver.model.Role;
import com.colorado.denver.model.User;
import com.colorado.denver.services.UserService;
import com.colorado.denver.services.persistence.SessionTools;
import com.colorado.denver.tools.GenericTools;
import com.colorado.denver.tools.Tools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * Keep this class clean! only main method and temporary experiments!
 */

@EnableWebMvc
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class DenverApplication extends SpringBootServletInitializer {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DenverApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Starting app!");

		// DO NOT DELETE THESE THREE LINES OF CODE I DARE YOU!
		try {
			SpringApplication.run(DenverApplication.class, args);
			// True = Use the update routine
			// If you want to rebuild the DB with CREATE use DenverDBSetupTest.java
			SessionTools.createSessionFactory(true);
			UserService.authorizeSystemuser();
			LOGGER.info("Successfully obtained system user Token. DB and security should be in healthy state");

		} catch (Exception e) {
			LOGGER.error("Error during starting up the app! Check the DB!");
			e.printStackTrace();
			SpringApplication.exit(GenericTools.getApplicationContext());
		}

		LOGGER.info("--------------BEGINNING JSON STUFF------------------");
		User u = UserService.getCurrentUser();

		Collection<Role> roles = new HashSet<Role>();;
		roles = u.getRoles();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonUser = gson.toJson(u);
		String roleOnSys = "";
		for (Iterator<Role> iterator = roles.iterator(); iterator.hasNext();) {
			Role type = iterator.next();
			System.out.println("Roles on User");
			System.out.println(type.getRoleName());
			roleOnSys = gson.toJson(type);

		}
		System.out.println();
		System.out.println("User");
		// Tools.printGson(jsonRoles);

		Tools.printGson(jsonUser);

		System.out.println();
		System.out.println("Role");
		Tools.printGson(roleOnSys);
		// User targetObject = new Gson().fromJson(jsonUser, User.class);
		// System.out.println(targetObject.getId());
		// System.out.println(targetObject.getObjectClass());
		// System.out.println(targetObject.getUsername());
		// System.out.println(targetObject.toString());

		LOGGER.info("--------------END JSON STUFF------------------");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DenverApplication.class);
	}

	public static String toJson(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.create();
		return json;
	}

}