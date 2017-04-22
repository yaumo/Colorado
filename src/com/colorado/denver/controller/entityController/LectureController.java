package com.colorado.denver.controller.entityController;

import java.util.Iterator;
import java.util.Set;

import org.slf4j.LoggerFactory;
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

@RestController
public class LectureController extends ObjectOperationController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8572242416817684426L;
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LectureController.class);

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Lecture.LECTURE, method = RequestMethod.POST)
	public Lecture handleLecturePostRequest() {

		String jsonString = GenericTools.getRequestBody();

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		Lecture entity = gson.fromJson(jsonString, Lecture.class);
		try {
			super.checkAccess(Lecture.LECTURE, DenverConstants.POST);
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
		}
		return (Lecture) super.doDatabaseOperation(entity, DenverConstants.POST);
	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Lecture.LECTURE, method = RequestMethod.PATCH)
	public Lecture handleLecturePatchRequest() {

		String jsonString = GenericTools.getRequestBody();

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		Lecture entity = gson.fromJson(jsonString, Lecture.class);
		try {
			super.checkAccess(Lecture.LECTURE, DenverConstants.PATCH);
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
		}
		return (Lecture) super.doDatabaseOperation(entity, DenverConstants.PATCH);
	}

	@RequestMapping(value = "/lecture", method = RequestMethod.GET)
	public Lecture getLectureForUser(@RequestParam(value = "lectureId", required = true) String lecId) {
		Course course = CourseService.getCourseForUser(UserService.getCurrentUser().getHibId());
		Set<Lecture> lectures = course.getLectures();
		for (Iterator iterator = lectures.iterator(); iterator.hasNext();) {
			Lecture lecture = (Lecture) iterator.next();
			if (lecture.getHibId().equals(lecId))
				return lecture;
		}
		return null;
	}
}
