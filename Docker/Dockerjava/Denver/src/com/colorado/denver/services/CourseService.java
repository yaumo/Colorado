package com.colorado.denver.services;

import java.util.List;
import java.util.Set;

import org.slf4j.LoggerFactory;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Course;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.model.Lecture;
import com.colorado.denver.model.Solution;
import com.colorado.denver.model.User;

public class CourseService {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CourseService.class);

	public static Course getCourseForUser(String userId) {
		HibernateController hibCtrl = new HibernateController();
		User usr = (User) hibCtrl.getEntity(userId);

		return usr.getCourse();
	}

	@SuppressWarnings("unchecked")
	public static List<Course> getAllCourses() {
		HibernateController hibCtrl = new HibernateController();

		return (List<Course>) (List<?>) hibCtrl.getEntityList(Course.class);
	}

	public static Lecture createSolutionsForCourseAsssignment(Lecture entity) {
		HibernateController hibCtrl = new HibernateController();

		if (entity.getExercises() != null) {
			LOGGER.error("WHY IST THIS NULL???");

			LOGGER.error("entity.getId:" + entity.getId());
		}
		Set<Exercise> exercises = entity.getExercises();

		Course course = entity.getCourse();
		Set<User> usersInCourse = course.getUsers();

		for (Exercise exc : exercises) {
			// TODO: deadline
			String title = exc.getTitle();
			String template = exc.getTemplate();

			for (User user : usersInCourse) {
				Solution sol = new Solution();
				sol.setTitle(title);
				sol.setCode(template);
				sol.setCorrect(false);
				sol.setExercise(exc);
				sol.setOwner(user);
				user.getSolutions().add(sol);

			}
		}
		return entity;

	}
}
