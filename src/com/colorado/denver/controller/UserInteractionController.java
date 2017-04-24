package com.colorado.denver.controller;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.colorado.denver.controller.entityController.ObjectOperationController;
import com.colorado.denver.controller.entityController.PrivilegeController;
import com.colorado.denver.model.Course;
import com.colorado.denver.model.User;
import com.colorado.denver.services.CourseService;
import com.colorado.denver.services.user.UserService;
import com.colorado.denver.tools.DenverConstants;
import com.colorado.denver.tools.GenericTools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@CrossOrigin
@RestController
public class UserInteractionController extends ObjectOperationController {
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout() {

		return "logout";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public List<Course> registrationGet() {
		return CourseService.getAllCourses();
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registrationPost() {

		String message = DenverConstants.ERROR;
		try {
			String json = GenericTools.getRequestBody();
			GsonBuilder gb = new GsonBuilder();
			gb.serializeNulls();
			Gson gson = gb.create();

			// We can serizalize the user here with the password itself because we use Gson.
			// The password will never be deserizalized back to Json
			User usr = (User) gson.fromJson(json, User.class);
			String salt = BCrypt.gensalt(12);
			usr.setPassword(BCrypt.hashpw(usr.getPassword(), salt));

			usr.getPrivileges().add(PrivilegeController.getPrivilegeByName(UserService.ROLE_STUDENT));

			HibernateController hibCtrl = new HibernateController();
			hibCtrl.addEntity(usr);
			// Save on DB. User needs to be existent when authorizing them
			super.doDatabaseOperation(usr, DenverConstants.POST);
			UserService.authorizeUser(usr);
			/*
			 * TODO:
			 * Add Registration key process. Set specific Docent or Student Role based on key. Return right
			 */
			message = Boolean.toString(UserService.isCurrentUserDocent());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return message;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public boolean login() {

		return UserService.isCurrentUserDocent();
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public Boolean changePassword(@RequestParam(value = "oldPassword", required = true) String oldPW,
			@RequestParam(value = "newPassword", required = true) String newPW) {
		User usr = UserService.getCurrentUser();

		if (!BCrypt.checkpw(oldPW, usr.getPassword())) {
			return false;
		} else {
			String salt = BCrypt.gensalt(12);
			usr.setPassword(BCrypt.hashpw(newPW, salt));

			HibernateController hibCtrl = new HibernateController();
			hibCtrl.mergeEntity(usr);

			return true;
		}
	}

}
