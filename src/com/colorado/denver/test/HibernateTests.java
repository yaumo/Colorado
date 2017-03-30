package com.colorado.denver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.services.persistence.HibernateGeneralTools;
import com.colorado.denver.services.persistence.SessionTools;

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
		SessionTools.createSessionFactory(true);// TRUE due to UPDATE!!!
		hibSession = SessionTools.sessionFactory.getCurrentSession();
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
		testAddEntity();
		hibCtrl.deleteEntity(exc);
	}

	@Test
	public void testUpdateGetEntity() {
		testAddEntity();

		exc.setDescription("HibernateTestUPDATE");

		hibCtrl.updateEntity(exc);
		Exercise exc2 = (Exercise) hibCtrl.getEntity(mainExcId, Exercise.class);
		assertEquals("UPDATE Test Failed. Description not updated through DB!", exc.getDescription(), exc2.getDescription());

	}

	@Test
	public void testMergeEntity() {
		testAddEntity();

		Exercise exc2 = new Exercise();

		// Manually. We 'fake' the same entity and parse it via hibernate first for all the fields.

		exc2.setDescription("HibernateTestMERGEBeforeSave");
		String fakeExcid = hibCtrl.addEntity(exc2);
		Exercise exerciseFake = (Exercise) hibCtrl.getEntity(fakeExcid, Exercise.class);
		exerciseFake.setDescription("HibernateTestMERGEAFTERSAVE");
		exerciseFake.setId(mainExcId);
		assertNotNull(hibCtrl.mergeEntity(exerciseFake));

		hibCtrl.deleteEntity(exc);
		hibCtrl.deleteEntity(exc2);

	}

	@After
	public void rollback() {
		hibSession.getTransaction().rollback();
		hibSession.close();
	}

}
