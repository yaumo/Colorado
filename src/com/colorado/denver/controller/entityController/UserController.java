package com.colorado.denver.controller.entityController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.model.Exercise;
import com.colorado.denver.model.Role;
import com.colorado.denver.model.User;
import com.colorado.denver.services.ExerciseService;
import com.colorado.denver.services.UserService;
import com.colorado.denver.tools.DenverConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class UserController extends ObjectOperationController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8278059823813912457L;
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + User.USER, method = RequestMethod.POST)
	public User userPost(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException, IOException {
		String jsonString = "";
		try {
			jsonString = super.checkRequest(request, DenverConstants.POST, User.USER);
			// TODO: Parse the "objectClass" like this: super.checkRequest(request, DenverConstants.POST, User.USER)
			// same in "doOperation down below and other controllers please"
			// We remove the "objectClass from the entities to make it more RESTful.. >.<
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

		User entity = gson.fromJson(jsonString, User.class);

		String jsonResponse = super.doOperation(entity, jsonString, DenverConstants.POST);
		entity = null;
		User entityAfterPost = gson.fromJson(jsonResponse, User.class);
		Link selfLink = linkTo(UserController.class).withSelfRel();
		entityAfterPost.add(selfLink);
		return entityAfterPost;
	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + User.USER, method = RequestMethod.PATCH)
	public User userPatch(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException, IOException {
		// JSONObject theObject = super.handleRequest(request, response);
		String jsonString = "";
		try {
			jsonString = super.checkRequest(request, DenverConstants.PATCH, User.USER);
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

		User entity = gson.fromJson(jsonString, User.class);

		String jsonResponse = super.doOperation(entity, jsonString, DenverConstants.PATCH);
		entity = null;
		User entityAfterPost = gson.fromJson(jsonResponse, User.class);
		Link selfLink = linkTo(UserController.class).withSelfRel();
		entityAfterPost.add(selfLink);
		return entityAfterPost;
	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + User.USER, method = RequestMethod.DELETE)
	public User userDelete(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException, IOException {
		// JSONObject theObject = super.handleRequest(request, response);
		String jsonString = "";
		try {
			jsonString = super.checkRequest(request, DenverConstants.DELETE, User.USER);
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

		User entity = gson.fromJson(jsonString, User.class);

		String jsonResponse = super.doOperation(entity, jsonString, DenverConstants.DELETE);
		entity = null;
		User entityAfterPost = gson.fromJson(jsonResponse, User.class);
		Link selfLink = linkTo(UserController.class).withSelfRel();
		entityAfterPost.add(selfLink);
		return entityAfterPost;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + User.USER, method = RequestMethod.GET)
	public User getUser() {
		User usr = UserService.getCurrentUser();

		Link selfLink = linkTo(methodOn(UserController.class).getUser()).withSelfRel();
		usr.add(selfLink);

		Link courseLink = linkTo(methodOn(CourseController.class).getCourseForUser(usr.getHibId())).withRel("course");
		usr.add(courseLink);
		return usr;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {

		return "login";
	}

	@RequestMapping(value = "/docent", method = RequestMethod.GET)
	public User getDocent() {

		User usr = UserService.getCurrentUser();
		Link selfLink = linkTo(methodOn(UserController.class).getDocent()).withSelfRel();
		usr.add(selfLink);

		return usr;

	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + User.USERS, method = RequestMethod.GET)
	public List<User> getAllUsers() {
		if (UserService.isCurrentUserDocent()) {
			return UserService.allUsers();
		} else {
			return null;
		}

	}

	public Set<Exercise> getExersisesForUser(String hibId) {
		return ExerciseService.getAllExercisesForUser(hibId);
	}

	public static Role calculateRoleBasedOnUser(User usr) {
		// Get the correct role!
		Role role = new Role();
		role.setRoleName("TestRole");// TODO: DELETE THIS!
		return role;
	}
}