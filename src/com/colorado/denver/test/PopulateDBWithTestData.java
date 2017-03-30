package com.colorado.denver.test;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Course;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.model.Lecture;
import com.colorado.denver.model.Solution;
import com.colorado.denver.services.persistence.HibernateGeneralTools;
import com.colorado.denver.services.persistence.SessionTools;

public class PopulateDBWithTestData {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PopulateDBWithTestData.class);
	private HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();

	@Before
	public void before() {

		boolean useUpdate = true;
		LOGGER.info("Creating session factory..");
		SessionTools.createSessionFactory(useUpdate);
		LOGGER.info("Done Creating session factory.");

	}

	@Test
	public void populateDatabase() {
		createCourse("WWI 14 SEA");
		createLecture("Programmieren I");
		createExercise();
		createSolution();
	}

	private Course createCourse(String course_name) {
		Course course = new Course();
		course.setCourse_title(course_name);
		hibCtrl.addEntity(course);
		return course;
	}

	private Exercise createExercise() {
		Exercise exercise = new Exercise();
		hibCtrl = HibernateGeneralTools.getHibernateController();
		hibCtrl.addEntity(exercise);
		return exercise;
	}

	private Solution createSolution() {
		Solution solution = new Solution();
		hibCtrl = HibernateGeneralTools.getHibernateController();
		hibCtrl.addEntity(solution);
		return solution;
	}

	private Lecture createLecture(String lecture_name) {
		Lecture lecture = new Lecture();
		lecture.setTitle(lecture_name);
		hibCtrl = HibernateGeneralTools.getHibernateController();
		hibCtrl.addEntity(lecture);
		return lecture;
	}
}
