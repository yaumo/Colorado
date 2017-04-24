package com.colorado.denver.controller.entityController;

import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.model.Course;
import com.colorado.denver.model.Lecture;
import com.colorado.denver.services.CourseService;
import com.colorado.denver.services.user.UserService;
import com.colorado.denver.tools.DenverConstants;
import com.colorado.denver.tools.GenericTools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@CrossOrigin
@RestController
public class LectureController extends ObjectOperationController {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LectureController.class);

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Lecture.LECTURE, method = RequestMethod.POST)
	public Lecture handleLecturePostRequest() {

		String jsonString = GenericTools.getRequestBody();

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		// gb.serializeNulls();
		gb.serializeNulls();
		Gson gson = gb.create();
		System.out.println(jsonString);

		Lecture entity = gson.fromJson(jsonString, Lecture.class);
		try {
			super.checkAccess(Lecture.LECTURE, DenverConstants.POST);
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
		}
		entity = (Lecture) super.doDatabaseOperation(entity, DenverConstants.POST);
		// We need to enforce the Casacading of the 1-n , n-1 relationship. (It's special)
		// Hibernate can not resolve this on its own therefore put on both directions.
		Course crs = entity.getCourse();

		crs.getLectures().add(entity);
		System.out.println("Updateing course");
		super.doDatabaseOperation(crs, DenverConstants.PATCH);

		System.out.println("Updated course succesfully");
		return entity;
	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Lecture.LECTURE, method = RequestMethod.PATCH)
	public Lecture handleLecturePatchRequest() {

		String jsonString = GenericTools.getRequestBody();

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();
		System.out.println(jsonString);
		Lecture entity = gson.fromJson(jsonString, Lecture.class);
		try {
			super.checkAccess(Lecture.LECTURE, DenverConstants.PATCH);
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
		}

		LOGGER.error("the request is: " + jsonString);
		entity = (Lecture) super.doDatabaseOperation(entity, DenverConstants.PATCH);
		entity = CourseService.createSolutionsForCourseAsssignment(entity);
		entity = (Lecture) super.doDatabaseOperation(entity, DenverConstants.PATCH);
		return entity;
	}

	@RequestMapping(value = "/lecture", method = RequestMethod.GET)
	public Lecture getLectureForUser(@RequestParam(value = "lectureId", required = true) String lecId) {
		LOGGER.error("I AM HERE!!!");
		Course course = CourseService.getCourseForUser(UserService.getCurrentUser().getId());
		LOGGER.error(UserService.getCurrentUser().getId());
		Set<Lecture> lectures = course.getLectures();
		for (Lecture lecture : lectures) {
			LOGGER.error(lecture.getId());
			if (lecture.getId().equals(lecId))
				return lecture;
		}

		return null;
	}
}
