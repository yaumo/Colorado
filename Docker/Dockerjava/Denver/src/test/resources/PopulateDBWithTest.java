package test.resources;

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
import com.colorado.denver.tools.DenverConstants;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PopulateDBWithTest.class)
public class PopulateDBWithTest {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PopulateDBWithTest.class);
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
		
		User docent1 = createUser("Heinrich Docent", "password", UserService.ROLE_DOCENT, "Heinrich", "Halt");
		User docent2 = createUser("Baum Docent", "password", UserService.ROLE_DOCENT, "Baum", "Gart");
		
		User student1c1 = createUser("Peter Student C1", "password", UserService.ROLE_STUDENT, "Peter", "Pasta");
		User student2c1 = createUser("Klaus Student C1", "password", UserService.ROLE_STUDENT, "Klaus", "Klausen");
		User student3c1 = createUser("Tom Student C1", "password", UserService.ROLE_STUDENT, "Tom", "Tomsen");
		User student4c1 = createUser("Karsten Student C1", "password", UserService.ROLE_STUDENT, "Karsten", "Karstensen");
		User student5c1 = createUser("Franz Student C1", "password", UserService.ROLE_STUDENT, "Franz", "Kanns");
		
		User student1c2 = createUser("Hans Student C2", "password", UserService.ROLE_STUDENT, "Hans", "Glueck");
		User student2c2 = createUser("Lukas Student C2", "password", UserService.ROLE_STUDENT, "Lukas", "Lokomotiv");
		User student3c2 = createUser("Johannes Student C2", "password", UserService.ROLE_STUDENT, "Johannes", "Hansen");
		User student4c2 = createUser("Mareike Student C2", "password", UserService.ROLE_STUDENT, "Mareike", "Marksen");
		User student5c2 = createUser("Martin Student C2", "password", UserService.ROLE_STUDENT, "Martin", "Martensen");		

		Set<User> usersDocent = new HashSet<User>();
		usersDocent.add(docent1);
		usersDocent.add(docent2);

		Set<User> usersStudentsC1 = new HashSet<User>();
		Set<User> usersStudentsC2 = new HashSet<User>();

		usersStudentsC1.add(student1c1);
		usersStudentsC1.add(student2c1);
		usersStudentsC1.add(student3c1);
		usersStudentsC1.add(student4c1);
		usersStudentsC1.add(student5c1);
		
		usersStudentsC2.add(student1c2);
		usersStudentsC2.add(student2c2);
		usersStudentsC2.add(student3c2);
		usersStudentsC2.add(student4c2);
		usersStudentsC2.add(student5c2);

		Course course1 = createCourse("C1 WWI 14 SEA");
		Course course2 = createCourse("C2 IMBIT 14 A");
		
		course1.setOwner(docent1);		
		course2.setOwner(docent2);

		student1c1.setCourse(course1);
		student2c1.setCourse(course1);
		student3c1.setCourse(course1);
		student4c1.setCourse(course1);
		student5c1.setCourse(course1);
		
		student1c2.setCourse(course2);
		student2c2.setCourse(course2);
		student3c2.setCourse(course2);
		student4c2.setCourse(course2);
		student5c2.setCourse(course2);
		
		Lecture lecture1c1 = createLecture("Programmieren I");
		Lecture lecture2c1 = createLecture("Programmieren II");
		
		Lecture lecture1c2 = createLecture("Programmieren III");
		Lecture lecture2c2 = createLecture("Programmieren IV");

		lecture1c1.setCourse(course1);
		lecture1c1.setCourse(course1);
		
		lecture1c2.setCourse(course2);
		lecture1c2.setCourse(course2);
		
		lecture1c1.setOwner(docent1);
		lecture2c1.setOwner(docent2);
		
		lecture1c2.setOwner(docent1);
		lecture2c2.setOwner(docent2);

		course1.setUsers(usersStudentsC1);
		course2.setUsers(usersStudentsC2);

		lecture1c1.setUsers(usersStudentsC1);
		lecture2c1.setUsers(usersStudentsC1);
		
		lecture1c2.setUsers(usersStudentsC2);
		lecture2c2.setUsers(usersStudentsC2);

		Set<Lecture> lecturesC1 = new HashSet<Lecture>();
		lecturesC1.add(lecture1c1);
		lecturesC1.add(lecture2c1);
		
		Set<Lecture> lecturesC2 = new HashSet<Lecture>();
		lecturesC2.add(lecture1c2);
		lecturesC2.add(lecture2c2);

		course1.setLectures(lecturesC1);
		
		course2.setLectures(lecturesC2);
		
		Exercise exercise1 = createExercise("Exercise 1", "2017-05-20", DenverConstants.JAVA, docent1);
		Exercise exercise2 = createExercise("Exercise 2", "2017-07-15", DenverConstants.JAVASCRIPT, docent2);
		
		Set<Exercise> exercisesL1C1 = new HashSet<Exercise>();
		exercisesL1C1.add(exercise1);
		
		Set<Exercise> exercisesL2C2 = new HashSet<Exercise>();
		exercisesL2C2.add(exercise2);
		
		lecture1c1.setExercises(exercisesL1C1);
		lecture2c2.setExercises(exercisesL2C2);

		hibCtrl.mergeEntity(docent1);
		hibCtrl.mergeEntity(docent2);
		
		hibCtrl.mergeEntity(student1c1);
		hibCtrl.mergeEntity(student2c1);
		hibCtrl.mergeEntity(student3c1);
		hibCtrl.mergeEntity(student4c1);
		hibCtrl.mergeEntity(student5c1);
		
		hibCtrl.mergeEntity(student1c2);
		hibCtrl.mergeEntity(student2c2);
		hibCtrl.mergeEntity(student3c2);
		hibCtrl.mergeEntity(student4c2);
		hibCtrl.mergeEntity(student5c2);
				
		hibCtrl.mergeEntity(course1);
		hibCtrl.mergeEntity(course2);
				
		hibCtrl.mergeEntity(lecture1c1);
		hibCtrl.mergeEntity(lecture2c1);
		
		hibCtrl.mergeEntity(lecture1c2);
		hibCtrl.mergeEntity(lecture2c2);
				
		hibCtrl.mergeEntity(exercise1);
		hibCtrl.mergeEntity(exercise2);
	}

	private User createUser(String name, String password, String roleName, String firstName, String lastName) {
		User usr = new User();
		usr.setUsername(name);
		usr.setFirstName(firstName);
		usr.setLastName(lastName);

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

	private Exercise createExercise(String title, String dueDate, String language, User owner) {
		Exercise exercise = new Exercise();
		exercise.setTitle(title);
		exercise.setDeadline(dueDate);
		exercise.setEntryMethod("fibonacci");
		exercise.setLanguage(language);
		exercise.setDescription("Create a function called fibonacci with Input int n.");
		exercise.setOwner(owner);
		exercise.setCreationDate("2017-04-25");
		
		String filename = "";
		String fileAsString = "";
		if(language.equals(DenverConstants.JAVASCRIPT))
			filename = "fibonacciJS.txt";
		else
			filename = "fibonacci.txt";
		
		//read Code for patternanswer
		try {
			InputStream is;
			is = new FileInputStream(filename);
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String line = buf.readLine();
			StringBuilder sb = new StringBuilder();

			while (line != null) {
				sb.append(line).append("\n");
				line = buf.readLine();
			}
			buf.close();
			fileAsString = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		exercise.setAnswer(fileAsString);
		
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
