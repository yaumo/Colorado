package com.colorado.denver.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;

import com.colorado.denver.model.BaseEntity;
import com.colorado.denver.model.EducationEntity;
import com.colorado.denver.services.persistence.HibernateSession;
import com.colorado.denver.services.user.UserService;
import com.colorado.denver.tools.GenericTools;

public class HibernateController {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HibernateController.class);
	/* Method to CREATE an entity in the database */

	public void setCreationInformation(EducationEntity entity) {

		// If EducationEntity set Owner!!!!
		if (UserService.getCurrentUser() == null) {
			LOGGER.error("Saved NULL CREATOR for entity: " + entity.getId());
		} else {
			LOGGER.info("Trying to get User from Authentication: " + UserService.getCurrentUser().getUsername());
			LOGGER.info("Setting user on Education entity: " + UserService.getCurrentUser().getUsername());
			entity.setOwner(UserService.getCurrentUser());
		}
		Date today = Calendar.getInstance().getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = sdf.format(today);
		entity.setCreationDate(dateString);

	}

	public String addEntity(BaseEntity<?> entity) {

		Session session = HibernateSession.sessionFactory.openSession();
		Transaction tx = null;
		String entityID = null;
		try {
			tx = session.beginTransaction();

			if (entity instanceof EducationEntity && entity.getClass() != EducationEntity.class) {
				// AssignableFrom doesn't work somehow...
				LOGGER.info("Setting creation information on EducationEntity");
				setCreationInformation((EducationEntity) entity);
			}

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
	public BaseEntity<?> updateEntity(BaseEntity<?> entity, String entityID) {
		Session session = HibernateSession.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			session.update(entity);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		BaseEntity<?> obj = getEntity(entityID);
		return obj;
	}

	/* Method to DELETE an entity from the records */
	public void deleteEntity(BaseEntity<?> entity) {
		Session session = HibernateSession.sessionFactory.openSession();
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

	/* Method to DELETE an entity from the records by id */
	public void deleteEntity(String id) {
		BaseEntity<?> entity = getEntity(id);
		Session session = HibernateSession.sessionFactory.openSession();
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

	public BaseEntity<?> getEntity(String id) {
		Class<?> clazz = null;
		BaseEntity<?> entity = null;

		// in order to do it the generic way..
		try {
			String[] parts = id.split("_");
			String clazzName = parts[0];
			LOGGER.info("Getting clazz: " + clazzName);
			clazz = GenericTools.getModelClassForName(clazzName);

		} catch (Exception e) {
			LOGGER.error("Error while getting class definition in getEntity()! ID correct? Class existing in model classpath?");
			e.printStackTrace();
		}

		Session session = HibernateSession.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(clazz);
			cr.add(Restrictions.eq("id", id));
			entity = (BaseEntity<?>) cr.uniqueResult();
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

	public BaseEntity<?> mergeEntity(BaseEntity<?> entity) {
		Session session = HibernateSession.sessionFactory.openSession();
		Transaction tx = null;
		// BaseEntity<?> toUpdate = getEntity(entity.getId());
		// toUpdate = Updater.updater(toUpdate, entity);
		BaseEntity<?> returnEnt = null;
		try {
			tx = session.beginTransaction();
			returnEnt = (BaseEntity<?>) session.merge(entity);
			LOGGER.info("Merged Entity: " + entity.getId());
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}

		return returnEnt;
	}

	/* Method to GET a list of entities from the records */
	@SuppressWarnings("unchecked")
	public List<BaseEntity<?>> getEntityList(Class<?> c) {
		List<BaseEntity<?>> entityList = null;
		Session session = HibernateSession.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(c);
			entityList = cr.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				e.printStackTrace();
		} finally {
			session.close();
		}
		return uniquifyList(entityList);
	}

	// We need unique results and Jackson cannot parse Sets... WTF Jackson? Get your shit together
	private List<BaseEntity<?>> uniquifyList(List<BaseEntity<?>> list) {
		return new ArrayList<BaseEntity<?>>(new HashSet<BaseEntity<?>>(list));

	}

}
