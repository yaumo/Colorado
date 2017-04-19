package com.colorado.denver.controller.entityController;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.model.Course;
import com.colorado.denver.model.Lecture;
import com.colorado.denver.services.CourseService;
import com.colorado.denver.services.UserService;
import com.colorado.denver.tools.DenverConstants;
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
	@ResponseBody
	public void handleLectureRequest(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException, IOException {
		// JSONObject theObject = super.handleRequest(request, response);
		String jsonString = "";
		try {
			jsonString = super.checkRequest(request, DenverConstants.POST, Lecture.LECTURE);
		} catch (JSONException e) {
			LOGGER.error("Error in OOC while handling the request: " + request.toString());
			e.printStackTrace();
		} catch (HttpServerErrorException e) {
			LOGGER.error("Not authorized to handle request:" + request.toString());
			e.printStackTrace();
			response.setStatus(403);
		}

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		Lecture entity = gson.fromJson(jsonString, Lecture.class);
		String jsonResponse = super.doOperation(entity, jsonString, DenverConstants.POST);
		entity = null; // Let GC run over this quickly
		response.setStatus(200);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().write(jsonResponse);
		response.getWriter().flush();
	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Lecture.LECTURE, method = RequestMethod.PATCH)
	public Lecture lecturePatch(HttpServletRequest request, HttpServletResponse response) throws ReflectionException, IOException {

		String jsonString = "";

		try {
			jsonString = super.checkRequest(request, DenverConstants.PATCH, Lecture.LECTURE);
		} catch (JSONException e) {
			LOGGER.error("Error in OOC while handling the request:" + request.toString());
			e.printStackTrace();
		} catch (HttpServerErrorException e) {
			LOGGER.error("Not authorized to handle request:" + request.toString());
			e.printStackTrace();
			response.setStatus(403);
		}

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		Lecture entity = gson.fromJson(jsonString, Lecture.class);
		String id = entity.getHibId();
		super.doOperation(entity, jsonString, DenverConstants.PATCH);
		return getLectureForUser(id);
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
