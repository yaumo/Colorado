package com.colorado.denver.controller;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.colorado.denver.model.User;
import com.colorado.denver.services.user.UserService;

@CrossOrigin
@RestController
public class UserInteractionController {
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {

		return "logout";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = false) String password) {
		User usr = new User();
		usr.setUsername(username);

		String salt = BCrypt.gensalt(12);
		usr.setPassword(BCrypt.hashpw(password, salt));

		HibernateController hibCtrl = new HibernateController();
		hibCtrl.addEntity(usr);
		return "Created User: " + usr.getUsername() + "password: " + usr.getPassword();
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public boolean login() {

		return UserService.isCurrentUserDocent();
	}

}
