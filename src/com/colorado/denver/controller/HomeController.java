package com.colorado.denver.controller;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.colorado.denver.DenverApplication;
import com.colorado.denver.DenverConstants;
import com.colorado.denver.model.Home;

@RestController
public class HomeController {

	private static final String template = "Hello, %s!";
	//private final AtomicLong counter = new AtomicLong();

	@RequestMapping(DenverConstants.FORWARD_SLASH + Home.HOME)
	public Home homeMessage(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Home(//counter.incrementAndGet(),
				String.format(template, name));
	}
	
	/* Method to CREATE a Home in the database */
	   public Long addHome(String content){
	      Session session = DenverApplication.factory.openSession();
	      Transaction tx = null;
	      Long homeID = null;
	      try{
	         tx = session.beginTransaction();
	         Home home = new Home(content);
	         homeID = (Long) session.save(home); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return homeID;
	   }
	   
	   /* Method to PRINT all the homes */
	   public void listHomes( ){
	      Session session = DenverApplication.factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         List homes = session.createQuery("FROM Home").list(); 
	         for (Iterator iterator = 
	        		 homes.iterator(); iterator.hasNext();){
	            Home home = (Home) iterator.next(); 
	            System.out.println("ID: " + home.getId()); 
	            System.out.println("Content: " + home.getContent()); 
	         }
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	   }
	   
	   /* Method to UPDATE content of a home */
	   public void updateHome(Long homeID, String content ){
	      Session session = DenverApplication.factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Home home = (Home)session.get(Home.class, homeID); 
	         home.setContent(content);
			 session.update(home); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	   }
	   
	   /* Method to DELETE a home from the records */
	   public void deleteHome(Long homeID){
	      Session session = DenverApplication.factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Home home = (Home)session.get(Home.class, homeID); 
	         session.delete(home); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	   }
}
