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
		// Create Exercise Entity
		assertNotNull("Entity creation FAILED!(Hibernate)", hibCtrl.addEntity(exc));
	}

	@Test
	public void testDeleteEntity() {
		// Delete Exercise Entity
		testAddEntity();
		HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();

	}

	@Test
	public void testUpdateGetEntity() {
		testAddEntity();

		HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();
		exc.setDescription("HibernateTestUPDATE");

		String id = hibCtrl.updateEntity(exc);
		Exercise exc2 = (Exercise) hibCtrl.getEntity(id, Exercise.class);
		assertEquals("UPDATE Test Failed. Description not updated through DB!", exc.getDescription(), exc2.getDescription());

	}

	@After
	public void rollback() {
		hibSession.getTransaction().rollback();
		hibSession.close();
	}

}
