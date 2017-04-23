package com.colorado.denver.controller.entityController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.model.Exercise;
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

		return UserService.getCurrentUser();

	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + User.USERS, method = RequestMethod.GET)
	public List<User> getAllUsers() {
		if (UserService.isCurrentUserDocent()) {

			List<User> usersList = UserService.allUsers();
			Set<User> set = new HashSet<User>(usersList);
			List<User> list = new ArrayList<User>(set);

			return list;
		} else {
			return null;
		}

	}

	public Set<Exercise> getExersisesForUser(String hibId) {
		return ExerciseService.getAllExercisesForUser(hibId);
	}

}