package com.colorado.denver.controller.entityController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.model.Exercise;
import com.colorado.denver.model.Privilege;
import com.colorado.denver.model.User;
import com.colorado.denver.services.ExerciseService;
import com.colorado.denver.services.user.UserService;
import com.colorado.denver.tools.DenverConstants;
import com.colorado.denver.tools.GenericTools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@CrossOrigin
@RestController
public class UserController extends ObjectOperationController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8278059823813912457L;
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + User.USER, method = RequestMethod.POST)
	public User handleUserPostRequest() {

		String jsonString = GenericTools.getRequestBody();

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		User entity = gson.fromJson(jsonString, User.class);
		try {
			super.checkAccess(User.USER, DenverConstants.POST);
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
		}
		return (User) super.doDatabaseOperation(entity, DenverConstants.POST);
	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + User.USER, method = RequestMethod.PATCH)
	public User handleUserPatchRequest() {

		String jsonString = GenericTools.getRequestBody();

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		User entity = gson.fromJson(jsonString, User.class);
		try {
			super.checkAccess(User.USER, DenverConstants.PATCH);
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
		}
		return (User) super.doDatabaseOperation(entity, DenverConstants.PATCH);
	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + User.USER, method = RequestMethod.GET)
	public User getUser() {
		User usr = UserService.getCurrentUser();
		return usr;
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

	public static Privilege calculatePrivilegeBasedOnUser(User usr) {
		// Get the correct role!
		Privilege role = new Privilege();
		role.setPrivilegeName("TestPrivilege");// TODO: DELETE THIS!
		return role;
	}
}