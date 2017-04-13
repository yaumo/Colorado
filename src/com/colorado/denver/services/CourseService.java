package com.colorado.denver.services;

import java.util.List;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Course;
import com.colorado.denver.model.User;

public class CourseService {

	public static Course getCourseForUser(String userId) {
		HibernateController hibCtrl = new HibernateController();
		User usr = (User) hibCtrl.getEntity(userId);

		return usr.getCourse();
	}

	public static List<Course> getAllCourses() {
		HibernateController hibCtrl = new HibernateController();

		return (List<Course>) (List<?>) hibCtrl.getEntityList(Course.class);
	}

}
