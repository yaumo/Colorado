package com.colorado.denver.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.controller.entityController.RoleController;
import com.colorado.denver.model.Course;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.model.Lecture;
import com.colorado.denver.model.Role;
import com.colorado.denver.model.Solution;
import com.colorado.denver.model.User;
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
	public void populateDatabase() throws IOException {
		UserService.authorizeSystemuser();
		User docent = createUser("Heinrich", "password", UserService.ROLE_DOCENT);
		User docent2 = createUser("Baum", "password", UserService.ROLE_DOCENT);

		User student = createUser("Peter", "password", UserService.ROLE_STUDENT);
		User student2 = createUser("Klause", "password", UserService.ROLE_STUDENT);
		User student3 = createUser("Tom", "password", UserService.ROLE_STUDENT);
		User student4 = createUser("Karsten", "password", UserService.ROLE_STUDENT);
		Set<User> users = new HashSet<User>();

		users.add(student);
		users.add(student2);
		users.add(student3);
		users.add(student4);

		Course course = createCourse("WWI 14 SEA");
		Course course2 = createCourse("WWI 15 SEB");
		Course course3 = createCourse("AI 14");
		Course course4 = createCourse("IMBIT 16 B");

		student.setCourse(course);
		student.setCourse(course2);
		student.setCourse(course2);
		student.setCourse(course3);

		Lecture lecture = createLecture("Programmieren I");
		Lecture lecture2 = createLecture("Programmieren II");
		Lecture lecture3 = createLecture("Programmieren III");
		Lecture lecture4 = createLecture("Programmieren für Gute");

		lecture3.setCourse(course3);
		lecture2.setCourse(course2);
		lecture.setCourse(course);
		lecture4.setCourse(course4);

		Set<Lecture> lectures = new HashSet<Lecture>();
		lectures.add(lecture);
		lectures.add(lecture2);
		lectures.add(lecture3);

		Exercise exercise = createExercise("Fibonacci");
		Set<Exercise> exercises = new HashSet<Exercise>();
		exercises.add(exercise);

		Solution solution = createSolution("Fibonacci Loesung");
		Set<Solution> solutions = new HashSet<Solution>();
		solutions.add(solution);

		solution.setExercise(exercise);

		// student.setLectures(lectures);
		// student.setExercises(exercises);
		student.setSolutions(solutions);
		student.setCourse(course);

		exercise.setOwner(docent2);
		exercise.setLectures(lectures); // setting on both entities gives error: lecture.setExercises(exercises); +
										// exercise.setLectures(lectures);

		course.setLectures(lectures);
		course.setUsers(users);
		course.setOwner(docent);

		lecture.setExercises(exercises);
		lecture.setOwner(docent);

		hibCtrl.updateEntity(solution);
		hibCtrl.updateEntity(student);
		hibCtrl.updateEntity(student2);
		hibCtrl.updateEntity(student3);
		hibCtrl.updateEntity(student4);
		hibCtrl.updateEntity(docent);
		hibCtrl.updateEntity(docent2);
		hibCtrl.updateEntity(exercise);
		hibCtrl.updateEntity(course);
		hibCtrl.updateEntity(course2);
		hibCtrl.updateEntity(course3);
		hibCtrl.updateEntity(course4);
		hibCtrl.updateEntity(lecture);
		hibCtrl.updateEntity(lecture2);
		hibCtrl.updateEntity(lecture3);
		hibCtrl.updateEntity(lecture4);
	}

	private User createUser(String name, String password, String roleName) {
		User usr = new User();
		usr.setUsername(name);
		usr.setPassword(password);
		Set<Role> roles = new HashSet<Role>();
		roles.add(RoleController.getRoleByName(roleName));
		usr.setRoles(roles);
		hibCtrl.addEntity(usr);
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
		exercise.setAnwswer(89 + "");
		exercise.setInput(11 + "");
		exercise.setInputType("int");
		exercise.setOutputType("int");
		hibCtrl.addEntity(exercise);

		return exercise;
	}

	private Solution createSolution(String title) throws IOException {

		InputStream is = new FileInputStream("fibonacci.txt");
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));
		String line = buf.readLine();
		StringBuilder sb = new StringBuilder();

		while (line != null) {
			sb.append(line).append("\n");
			line = buf.readLine();
		}
		buf.close();
		String fileAsString = sb.toString();

		Solution solution = new Solution();
		solution.setTitle(title);

		solution.setCode(fileAsString);
		solution.setSubmitted(false);
		solution.setHasBeenModified(false);

		hibCtrl.addEntity(solution);
		return solution;
	}

	private Lecture createLecture(String lecture_name) {
		Lecture lecture = new Lecture();
		lecture.setTitle(lecture_name);
		hibCtrl.addEntity(lecture);
		return lecture;
	}
}
