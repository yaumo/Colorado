package com.colorado.denver.controller.entityController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.colorado.denver.model.Course;
import com.colorado.denver.services.CourseService;
import com.colorado.denver.services.UserService;
import com.colorado.denver.tools.DenverConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class CourseController extends ObjectOperationController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2330023617204647488L;
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Course.COURSE, method = RequestMethod.POST)
	@ResponseBody
	public void handleCourseRequest(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException, IOException {
		UserService.authorizeSystemuser();
		String jsonString = DenverConstants.ERROR;
		try {
			jsonString = super.checkRequest(request);
		} catch (JSONException e) {
			LOGGER.error("Error in OOC while handling the request: " + request.toString());
			e.printStackTrace();
		}

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		Gson gson = gb.create();

		Course entity = gson.fromJson(jsonString, Course.class);
		String jsonResponse = super.doCrud(entity, jsonString);
		entity = null; // Let GC run over this quickly
		response.setStatus(200);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().write(jsonResponse);
		response.getWriter().flush();
	}

	public Course getCourseForUser() {
		UserService.authorizeSystemuser();

		return getCourseForUser(UserService.getCurrentUser().getHibId());
	}

	@RequestMapping(value = "/course", method = RequestMethod.GET)
	public Course getCourseForUser(@RequestParam(value = "user", required = false) String hibId) {

		Course course = CourseService.getCourseForUser(hibId);
		if (course != null) {
			Link selfLink = linkTo(UserController.class).slash(Course.COURSE).withSelfRel();
			course.add(selfLink);
		}

		return course;
	}

	@RequestMapping(value = "/courses", method = RequestMethod.GET)
	public List<Course> getAllCourses() {
		// Add docent security!
		List<Course> courses = CourseService.getAllCourses();

		if (!courses.isEmpty()) {
			for (Iterator iterator = courses.iterator(); iterator.hasNext();) {
				Course course = (Course) iterator.next();
				Link selfLink = linkTo(CourseController.class).withSelfRel();
				course.add(selfLink);

			}
		}

		return courses;
	}

}
