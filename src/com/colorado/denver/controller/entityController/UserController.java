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
		UserService.authorizeSystemuser();
		// JSONObject theObject = super.handleRequest(request, response);
		String jsonString = "";
		try {
			jsonString = super.checkRequest(request);
		} catch (JSONException e) {
			LOGGER.error("Error in OOC while handling the request: " + request.toString());
			e.printStackTrace();
		}

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		User entity = gson.fromJson(jsonString, User.class);

		String jsonResponse = super.doCrud(entity, jsonString);
		entity = null;
		User entityAfterCrud = gson.fromJson(jsonResponse, User.class);
		Link selfLink = linkTo(UserController.class).withSelfRel();
		entityAfterCrud.add(selfLink);
		return entityAfterCrud;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + User.USER, method = RequestMethod.GET)
	public User getUser() {
		UserService.authorizeSystemuser();

		User usr = UserService.getCurrentUser();
		Link selfLink = linkTo(methodOn(UserController.class).getUser()).withSelfRel();
		usr.add(selfLink);
		System.out.println("Course On user: " + usr.getCourse().getHibId());
		Link courseLink = linkTo(methodOn(CourseController.class).getCourseForUser(usr.getHibId())).withRel("course");
		usr.add(courseLink);
		return usr;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

	@RequestMapping(value = "/docent", method = RequestMethod.GET)
	public User getDocent() {
		UserService.authorizeSystemuser();

		User usr = UserService.getCurrentUser();
		Link selfLink = linkTo(methodOn(UserController.class).getDocent()).withSelfRel();
		usr.add(selfLink);

		return usr;

	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + User.USERS, method = RequestMethod.GET)
	public List<User> getAllUsers() {
		List<User> allUsers = UserService.allUsers();
		for (User user : allUsers) {
			Link courseLink = linkTo(methodOn(CourseController.class).getCourseForUser(user.getHibId())).withRel("course");
			user.add(courseLink);
		}
		return allUsers;
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