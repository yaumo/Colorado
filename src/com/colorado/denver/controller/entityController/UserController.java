package com.colorado.denver.controller.entityController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.colorado.denver.model.Role;
import com.colorado.denver.model.User;
import com.colorado.denver.services.UserService;

@RestController
public class UserController extends ObjectOperationController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
		// TODO: simple validation userValidator.validate(userForm,
		// bindingResult);

		if (bindingResult.hasErrors()) {
			return "registration";
		}
		// Role role = calculateRoleBasedOnUser(userForm);
		userService.save(userForm);
		// userService.registerNewUserAccount(userForm);

		return "redirect:/welcome";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcome(Model model) {
		return "welcome";
	}

	public static Role calculateRoleBasedOnUser(User usr) {
		// Get the correct role!
		Role role = new Role();
		role.setRoleName("TestRole");// TODO: DELETE THIS!
		return role;
	}
}