package com.colorado.denver.controller.entityController;

import java.io.IOException;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.BaseEntity;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.services.persistence.HibernateGeneralTools;
import com.colorado.denver.tools.DenverConstants;
import com.google.gson.Gson;

@RestController
public class ExerciseController extends ObjectOperationController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6133518159703299316L;
	private HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExerciseController.class);

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Exercise.EXERCISE, method = RequestMethod.POST)
	@ResponseBody
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException, IOException {
		JSONObject theObject = super.handleRequest(request, response, this);

		int crud = 0;
		String id = "ERROR";

		try {
			crud = theObject.getInt(BaseEntity.ID);
			id = theObject.getString(BaseEntity.ID);
		} catch (Exception e) {
			LOGGER.error("Error extraxting the JSON: " + theObject.toString());
			e.printStackTrace();
		}

		// Prepare for CRUD and response

		Gson gson = new Gson();
		String json = null;

		switch (crud) {
		case 1:
			// Create then get from DB for id then update with content
			json = gson.toJson(update(read(create()), theObject));
			break;
		case 2:
			json = gson.toJson(read(id));
			break;
		case 3:

			json = gson.toJson(update(read(id), theObject));
			break;
		case 4:
			delete(id);
			break;

		default:
			LOGGER.error("ERROR IN ASSERTING A CRUD OPERTAION!! CRUD VALUE: " + crud);
			throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);// last resort
		}

		// prepare response

		// json = gson.toJson(//created object )

	}

	private String create() {
		Exercise exc = new Exercise();
		String id = hibCtrl.addEntity(exc);
		return id;
	}

	private Exercise read(String id) {
		return (Exercise) hibCtrl.getEntity(id);
	}

	private Exercise update(Exercise exc, JSONObject theObject) {

		return exc;
	}

	private boolean delete(String id) {

		return false;
	}

	private JSONObject updateTwoJson(JSONObject newObj, JSONObject oldObj) {
		String key = "key1"; // whatever

		newObj.keys();
		String val_newer;
		try {
			val_newer = newObj.getString(key);

			String val_older = oldObj.getString(key);

			// Compare values
			if (!val_newer.equals(val_older)) {
				// Update value in object
				newObj.put(key, val_newer);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newObj;

	}

}
