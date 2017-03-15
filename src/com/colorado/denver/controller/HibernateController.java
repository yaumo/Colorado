package com.colorado.denver.controller;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.colorado.denver.DenverApplication;
import com.colorado.denver.model.BaseEntity;
import com.colorado.denver.model.Home;

public class HibernateController {

		/* Method to CREATE an entity in the database */
	   public String addEntity(BaseEntity entity){
	      Session session = DenverApplication.factory.openSession();
	      Transaction tx = null;
	      String entityID = null;
	      try{
	         tx = session.beginTransaction();
	         entityID = (String) session.save(entity); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return entityID;
	   }
	
	   /* Method to UPDATE an entity */
	   public void updateEntity(BaseEntity entity){
	      Session session = DenverApplication.factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();

	         //update given Home
	         session.update(entity); 
	         
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	   }
	   
	   
	   /* Method to DELETE an entity from the records */
	   public void deleteEntity(BaseEntity entity){
	      Session session = DenverApplication.factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         session.delete(entity); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	   }
	   
	   /* Method to GET an entity from the records */
	   public BaseEntity getEntity(String id, Class c){
		  BaseEntity entity = null; 
	      Session session = DenverApplication.factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Criteria cr = session.createCriteria(c);
	         cr.add(Restrictions.eq("id", id));
	         entity = (BaseEntity)cr.uniqueResult();
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return entity;
	   }
	   
	   /* Method to GET a list of entities from the records */
	   public List<BaseEntity> getEntityList(Class c){
		  List<BaseEntity> entityList = null; 
	      Session session = DenverApplication.factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Criteria cr = session.createCriteria(c);
	         entityList = cr.list();
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return entityList;
	   }
	   
}