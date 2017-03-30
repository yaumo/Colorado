package com.colorado.denver.controller.entityController;

import java.io.IOException;
import java.util.Iterator;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.BaseEntity;
import com.colorado.denver.model.Role;
import com.colorado.denver.model.User;
import com.colorado.denver.services.persistence.HibernateGeneralTools;
import com.colorado.denver.tools.DenverConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class UserController extends ObjectOperationController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8278059823813912457L;
	private HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + User.USER, method = RequestMethod.POST)
	@ResponseBody
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException, IOException {
		JSONObject theObject = super.handleRequest(request, response, this);

		String jsonReq = DenverConstants.ERROR;
		int crud = 0;
		String id = DenverConstants.ERROR;

		try {
			crud = theObject.getInt(BaseEntity.ID);
			id = theObject.getString(BaseEntity.ID);
			jsonReq = theObject.getString(DenverConstants.JSON);
			theObject.remove(DenverConstants.JSON);
		} catch (Exception e) {
			LOGGER.error("Error extraxting the JSON: " + theObject.toString());
			e.printStackTrace();
		}

		// Prepare for CRUD and response
		User targetObject = new Gson().fromJson(jsonReq, User.class);
		Gson gson = new Gson();
		String respJson = "";
		switch (crud) {
		case 1:
			// Merge should trigger the update itself!
			respJson = gson.toJson(read(create(targetObject)));
			break;
		case 2:
			respJson = gson.toJson(read(id));
			break;
		case 3:
			respJson = gson.toJson(update(targetObject));
			break;
		case 4:
			if (delete(id)) {
				respJson = "true";
			} else {
				LOGGER.error("Error deleting entity: " + id);
				respJson = "false";
			}
			break;

		default:
			LOGGER.error("ERROR IN ASSERTING A CRUD OPERTAION!! CRUD VALUE: " + crud);
			throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);// last resort
		}

		// prepare response

		// json = gson.toJson(//created object )

	}

	private String create(User newObject) {
		String id = hibCtrl.addEntity(newObject);
		return id;
	}

	private User read(String id) {
		return (User) hibCtrl.getEntity(id, User.class);
	}

	private User update(User newObject) {
		hibCtrl.mergeEntity(newObject);
		return null;
	}

	private boolean delete(String id) {

		return false;
	}

	public String buildJson(Object u) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String jsonRoles = gson.toJson(u);
		return jsonRoles;
	}

	private JSONObject updateTwoJson(JSONObject object1, JSONObject object2) throws JSONException {
		JSONObject mergedObj = new JSONObject();

		Iterator i1 = object1.keys();
		Iterator i2 = object2.keys();
		String tmp_key;
		while (i1.hasNext()) {
			tmp_key = (String) i1.next();
			mergedObj.put(tmp_key, object1.get(tmp_key));
		}
		while (i2.hasNext()) {
			tmp_key = (String) i2.next();
			mergedObj.put(tmp_key, object2.get(tmp_key));
		}
		return mergedObj;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "registration";
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