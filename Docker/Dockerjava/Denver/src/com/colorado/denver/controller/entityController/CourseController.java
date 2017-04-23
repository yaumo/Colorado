package com.colorado.denver.controller.entityController;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.model.Course;
import com.colorado.denver.services.CourseService;
import com.colorado.denver.services.user.UserService;
import com.colorado.denver.tools.DenverConstants;
import com.colorado.denver.tools.GenericTools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@CrossOrigin
@RestController
public class CourseController extends ObjectOperationController {

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Course.COURSE, method = RequestMethod.POST)
	@ResponseBody
	public Course handleCoursePostRequest() {

		String jsonString = GenericTools.getRequestBody();

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		Course entity = gson.fromJson(jsonString, Course.class);
		try {
			super.checkAccess(Course.COURSE, DenverConstants.POST);
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
		}
		return (Course) super.doDatabaseOperation(entity, DenverConstants.POST);
	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Course.COURSE, method = RequestMethod.PATCH)
	@ResponseBody
	public Course handleCoursePatchRequest() {

		String jsonString = GenericTools.getRequestBody();

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		Course entity = gson.fromJson(jsonString, Course.class);
		try {
			super.checkAccess(Course.COURSE, DenverConstants.PATCH);
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
		}
		return (Course) super.doDatabaseOperation(entity, DenverConstants.PATCH);
	}

	@RequestMapping(value = "/course", method = RequestMethod.GET)
	public Course getCourseForUser(@RequestParam(value = "user", required = false) String hibId) {
		if (hibId == null) {
			return UserService.getCurrentUser().getCourse();
		} else if (UserService.isCurrentUserDocent()) {
			return UserService.getUserById(hibId).getCourse();
		} else {
			throw new AccessDeniedException(DenverConstants.STUDENT_ACCES_DENIED);
		}
	}

	@RequestMapping(value = "/courses", method = RequestMethod.GET)
	public List<Course> getAllCourses() {
		if (UserService.isCurrentUserDocent()) {
			return CourseService.getAllCourses();
		} else {
			throw new AccessDeniedException(DenverConstants.STUDENT_ACCES_DENIED);
		}

	}

}
