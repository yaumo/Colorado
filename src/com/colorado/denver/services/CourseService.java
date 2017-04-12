package com.colorado.denver.services;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Course;
import com.colorado.denver.model.User;

public class CourseService {

	public static Course getCourseForUser(String userId) {
		HibernateController hibCtrl = new HibernateController();
		User usr = (User) hibCtrl.getEntity(userId);

		return usr.getCourse();
	}

}
