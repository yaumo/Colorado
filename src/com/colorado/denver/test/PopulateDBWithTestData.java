package com.colorado.denver.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Course;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.model.Lecture;
import com.colorado.denver.model.Solution;
import com.colorado.denver.services.UserService;
import com.colorado.denver.services.persistence.HibernateGeneralTools;
import com.colorado.denver.services.persistence.SessionTools;

@RunWith(SpringRunner.class)
@SpringBootTest
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
		createDocent("Heinrich", "password", UserService.ROLE_GLOBAL_ADMINISTRATOR);
		createCourse("WWI 14 SEA");
		createLecture("Programmieren I");
		createExercise("Fibonacci");
		createSolution("Fibonacci Loesung");
	}

	private User createDocent(String name, String password, String roleName) {
		User usr = new User();
		usr.setName(name);
		usr.setPassword(password);
		return usr;
	}

	private Course createCourse(String course_name) {
		Course course = new Course();
		course.setTitle(course_name);
		hibCtrl.addEntity(course);
		return course;
	}

	private Exercise createExercise(String title) {
		Exercise exercise = new Exercise();
		exercise.setTitle(title);
		hibCtrl = HibernateGeneralTools.getHibernateController();
		hibCtrl.addEntity(exercise);
		return exercise;
	}

	private Solution createSolution(String title) {
		Solution solution = new Solution();
		solution.setTitle(title);
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
