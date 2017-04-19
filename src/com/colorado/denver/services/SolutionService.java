package com.colorado.denver.services;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.LoggerFactory;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Course;
import com.colorado.denver.model.Lecture;
import com.colorado.denver.model.Solution;
import com.colorado.denver.model.User;

public class SolutionService {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SolutionService.class);

	public static Set<Solution> getAllSollutionsBasedCriteria(String excId, String lectureId) {

		HibernateController hibCtrl = new HibernateController();
		Set<Solution> sols = new HashSet<>();

		Lecture lect = (Lecture) hibCtrl.getEntity(lectureId);

		Course crs = lect.getCourse();
		Set<User> users = crs.getUsers();

		for (User user : users) {
			Set<Solution> userSolution = user.getSolutions();
			for (Solution solution : userSolution) {
				if (solution.getExercise().getHibId().equals(excId)) {
					sols.add(solution);
				}
			}
		}
		return sols;
	}

	public static Solution getSollutionBasedCriteria(String excId, String userId) {
		User usr = UserService.getUserById(userId);
		Set<Solution> sols = usr.getSolutions();
		for (Solution solution : sols) {
			if (solution.getExercise().getHibId().equals(excId)) {
				return solution;
			}

		}
		return null;
	}

}
