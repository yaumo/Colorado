package com.colorado.denver.services;

import java.util.Set;

import org.slf4j.LoggerFactory;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Solution;
import com.colorado.denver.model.User;

public class SolutionService {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SolutionService.class);

	public static Set<Solution> getAllSolutionsForUser(String userId) {

		HibernateController hibCtrl = new HibernateController();

		User usr = (User) hibCtrl.getEntity(userId);
		return usr.getSolutions();
	}

}
