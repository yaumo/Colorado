package com.colorado.denver.controller;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;

import com.colorado.denver.model.BaseEntity;
import com.colorado.denver.services.UserService;
import com.colorado.denver.services.persistence.SessionTools;
import com.colorado.denver.tools.GenericTools;

public class HibernateController {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HibernateController.class);
	/* Method to CREATE an entity in the database */

	public void setCreationInformation(BaseEntity<?> clazz) {
		clazz.setObjectClass(GenericTools.returnClassName(clazz).toLowerCase());
		clazz.setCreationDate(new Date());

		if (UserService.getCurrentUser() == null) {
			LOGGER.info("Saved NULL CREATOR for entity: " + clazz.getObjectClass());
			clazz.setCreator(null);// TODO: NOT CLEAN!!!
		} else {
			LOGGER.info("Trying to get User from Authentication: " + UserService.getCurrentUser().getUsername());
			clazz.setCreator(UserService.getCurrentUser());
		}

	}

	public String addEntity(BaseEntity<?> entity) {

		Session session = SessionTools.sessionFactory.openSession();
		Transaction tx = null;
		String entityID = null;
		try {
			setCreationInformation(entity);
			tx = session.beginTransaction();
			LOGGER.info("Saving entity: " + entity.getClass().getName());
			entityID = (String) session.save(entity);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return entityID;
	}

	/* Method to UPDATE an entity */
	public String updateEntity(BaseEntity entity) {
		Session session = SessionTools.sessionFactory.openSession();
		Transaction tx = null;
		String entityID = null;
		entityID = entity.getId();
		try {
			tx = session.beginTransaction();

			// update given Home
			session.update(entity);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return entityID;
	}

	/* Method to DELETE an entity from the records */
	public void deleteEntity(BaseEntity entity) {
		Session session = SessionTools.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(entity);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/* Method to GET an entity from the records */
	public BaseEntity getEntity(String id, Class c) {
		BaseEntity entity = null;
		Session session = SessionTools.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(c);
			cr.add(Restrictions.eq("id", id));
			entity = (BaseEntity) cr.uniqueResult();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return entity;
	}

	/* Method to GET a list of entities from the records */
	public List<BaseEntity> getEntityList(Class c) {
		List<BaseEntity> entityList = null;
		Session session = SessionTools.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(c);
			entityList = cr.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return entityList;
	}

}
