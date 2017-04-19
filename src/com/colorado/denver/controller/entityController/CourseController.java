package com.colorado.denver.controller.entityController;

import java.io.IOException;
import java.util.List;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

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
		String jsonString = DenverConstants.ERROR;
		try {
			jsonString = super.checkRequest(request, DenverConstants.POST, Course.COURSE);
		} catch (JSONException e) {
			LOGGER.error("Error in OOC while handling the request: " + request.toString());
			e.printStackTrace();
		} catch (HttpServerErrorException e) {
			LOGGER.error("Not authorized to handle request:" + request.toString());
			e.printStackTrace();
			response.setStatus(403);
		}

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		Gson gson = gb.create();

		Course entity = gson.fromJson(jsonString, Course.class);
		String jsonResponse = super.doOperation(entity, jsonString, DenverConstants.POST);
		entity = null; // Let GC run over this quickly
		response.setStatus(200);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().write(jsonResponse);
		response.getWriter().flush();
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
