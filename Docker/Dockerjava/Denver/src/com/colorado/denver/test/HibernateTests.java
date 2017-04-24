package com.colorado.denver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.services.persistence.HibernateGeneralTools;
import com.colorado.denver.services.persistence.HibernateSession;
import com.colorado.denver.services.user.UserService;
import com.colorado.denver.tools.Tools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HibernateTests {

	public HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();
	Exercise exc;
	Session hibSession;
	String mainExcId;

	@Before
	public void prepareTest() {
		// Activate Hibernate:
		HibernateSession.createSessionFactory(true);// TRUE due to UPDATE!!!
		hibSession = HibernateSession.sessionFactory.getCurrentSession();
		assertNotNull(hibSession);
		exc = new Exercise();
		exc.setTitle("HibTest");
		exc.setDescription("HibernateTests");
	}

	@Test
	public void testAddEntity() {
		mainExcId = hibCtrl.addEntity(exc);
		assertNotNull("Entity creation FAILED!(Hibernate)", mainExcId);
	}

	@Test
	public void testDeleteEntity() {
		// Delete Exercise Entity
		hibCtrl.deleteEntity(exc);
	}

	@Test
	@Ignore // update discontinued
	public void testUpdateGetEntity() {
		exc.setDescription("HibernateTestUPDATE");

		hibCtrl.updateEntity(exc, exc.getId());
		Exercise exc2 = (Exercise) hibCtrl.getEntity(mainExcId);
		assertEquals("UPDATE Test Failed. Description not updated through DB!", exc.getDescription(), exc2.getDescription());

	}

	@Test
	public void testMergeEntity() {

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();
		Exercise excToChange = new Exercise();
		excToChange.setPatternSolution("Default code before save");
		String id = hibCtrl.addEntity(excToChange);
		excToChange = (Exercise) hibCtrl.getEntity(id);

		String jsonExcToChange = gson.toJson(excToChange);
		System.out.println("Printing Exercise actual:");

		Tools.printGson(jsonExcToChange);

		UserService.authorizeSystemuser();
		Exercise excToChangeWith = new Exercise();
		excToChangeWith.setPatternSolution("THis is the new code");
		excToChangeWith.setDescription("Des2");

		excToChangeWith.setOwner(UserService.getCurrentUser());
		excToChangeWith.setId(id);
		// Manually. We 'fake' the same entity and parse it via hibernate first for all the fields.

		String jsonExcToChangeWithBeforeMerge = gson.toJson(excToChangeWith);
		System.out.println("Printing Exercise new before merge:");

		Tools.printGson(jsonExcToChangeWithBeforeMerge);

		hibCtrl.mergeEntity(excToChangeWith);
		Exercise excMerged = (Exercise) hibCtrl.getEntity(id);

		String jsonExcToChangeWithAfterMerge = gson.toJson(excMerged);
		System.out.println("Printing Exercise new after merge:");

		Tools.printGson(jsonExcToChangeWithAfterMerge);

	}

	@After
	public void rollback() {

	}

}
