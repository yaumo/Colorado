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
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.controller.entityController.PrivilegeController;
import com.colorado.denver.model.Course;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.model.Lecture;
import com.colorado.denver.model.Privilege;
import com.colorado.denver.model.Solution;
import com.colorado.denver.model.User;
import com.colorado.denver.services.persistence.HibernateGeneralTools;
import com.colorado.denver.services.persistence.HibernateSession;
import com.colorado.denver.services.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PopulateDBWithTestData {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PopulateDBWithTestData.class);
	private HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();

	@Before
	public void before() {

		boolean useUpdate = true;
		LOGGER.info("Creating session factory..");
		HibernateSession.createSessionFactory(useUpdate);
		LOGGER.info("Done Creating session factory.");

	}

	@Test
	public void populateDatabase() throws IOException {
		UserService.authorizeSystemuser();
		User docent = createUser("Heinrich Docent", "password", UserService.ROLE_DOCENT);
		User tutor1 = createUser("Baum Tutor L2", "password", UserService.ROLE_TUTOR);
		User tutor2 = createUser("Tim Tutor L2", "password", UserService.ROLE_TUTOR);

		User student = createUser("Peter Student C1", "password", UserService.ROLE_STUDENT);
		User student2 = createUser("Klaus Student C1", "password", UserService.ROLE_STUDENT);
		User student3 = createUser("Tom Student C1", "password", UserService.ROLE_STUDENT);
		User student4 = createUser("Karsten Student C3", "password", UserService.ROLE_STUDENT);

		Set<User> usersTutors = new HashSet<User>();
		usersTutors.add(tutor1);
		usersTutors.add(tutor2);

		Set<User> usersStudentsC1 = new HashSet<User>();
		Set<User> usersStudentsC2 = new HashSet<User>();
		Set<User> usersStudentsC3 = new HashSet<User>();

		usersStudentsC1.add(student);
		usersStudentsC1.add(student2);
		usersStudentsC1.add(student3);
		usersStudentsC3.add(student4);

		Course course = createCourse("WWI 14 SEA");
		Course course2 = createCourse("WWI 15 SEA");
		Course course3 = createCourse("IMBIT 14 A");
		Course course4 = createCourse("AI 16 C");

		student.setCourse(course);
		student2.setCourse(course);
		student3.setCourse(course);
		student4.setCourse(course2);
		Lecture lecture = createLecture("Programmieren I");
		Lecture lecture2 = createLecture("Programmieren II");
		Lecture lecture3 = createLecture("Programmieren III");
		Lecture lecture4 = createLecture("Programmieren IV");

		lecture3.setCourse(course3);
		lecture2.setCourse(course2);
		lecture.setCourse(course);
		lecture4.setCourse(course4);

		course.setUsers(usersStudentsC1);
		course2.setUsers(usersStudentsC2);
		course3.setUsers(usersStudentsC3);

		lecture2.setUsers(usersTutors);

		Set<Lecture> lectures = new HashSet<Lecture>();
		lectures.add(lecture);
		lectures.add(lecture2);
		lectures.add(lecture3);

		course.setLectures(lectures);
		course.setOwner(docent);

		lecture.setOwner(docent);

		hibCtrl.mergeEntity(student);
		hibCtrl.mergeEntity(student2);
		hibCtrl.mergeEntity(student3);
		hibCtrl.mergeEntity(student4);
		hibCtrl.mergeEntity(docent);
		hibCtrl.mergeEntity(tutor1);
		hibCtrl.mergeEntity(tutor2);
		hibCtrl.mergeEntity(course);
		hibCtrl.mergeEntity(course2);
		hibCtrl.mergeEntity(course3);
		hibCtrl.mergeEntity(course4);
		hibCtrl.mergeEntity(lecture);
		hibCtrl.mergeEntity(lecture2);
		hibCtrl.mergeEntity(lecture3);
		hibCtrl.mergeEntity(lecture4);

	}

	private User createUser(String name, String password, String roleName) {
		User usr = new User();
		usr.setUsername(name);

		String salt = BCrypt.gensalt(12);
		usr.setPassword(BCrypt.hashpw(password, salt));
		Set<Privilege> roles = new HashSet<Privilege>();
		roles.add(PrivilegeController.getPrivilegeByName(roleName));
		usr.setPrivileges(roles);
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
