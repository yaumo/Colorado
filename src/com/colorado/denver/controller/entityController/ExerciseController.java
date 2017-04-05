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
import com.google.gson.GsonBuilder;

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
	public HttpServletResponse handleExerciseRequest(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException, IOException {

		JSONObject theObject = super.handleRequest(request, response);

		int crud = 0;
		String id = "ERROR";
		String json = null;

		try {
			crud = theObject.getInt(BaseEntity.ID);
			id = theObject.getString(BaseEntity.ID);
			json = theObject.getString(DenverConstants.JSON);
		} catch (Exception e) {
			LOGGER.error("Error extraxting the JSON: " + theObject.toString());
			e.printStackTrace();
		}

		// Prepare for CRUD and response
		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		Exercise entity = gson.fromJson(json, Exercise.class);

		String jsonResponse = "NO RESPONSE";

		switch (crud) {
		case 1:
			create();
			jsonResponse = gson.toJson(update(entity));
			break;
		case 2:
			jsonResponse = gson.toJson(read(id));
			break;
		case 3:
			jsonResponse = gson.toJson(update(entity));
			break;
		case 4:
			delete(id);
			jsonResponse = "SUCCESS";
			break;

		default:
			LOGGER.error("ERROR IN ASSERTING A CRUD OPERTAION!! CRUD VALUE: " + crud);
			throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);// last resort
		}

		// prepare response
		response.setStatus(200);
		response.getWriter().write(jsonResponse);
		response.getWriter().flush();
		// response.getWriter().close(); maybe no close because I didn't open this?
		return response;
	}

	private String create() {
		Exercise exc = new Exercise();
		String id = hibCtrl.addEntity(exc);
		return id;
	}

	private BaseEntity<?> read(String id) {
		return hibCtrl.getEntity(id);
	}

	private BaseEntity<?> update(BaseEntity<?> entity) {
		return hibCtrl.mergeEntity(entity);
	}

	private boolean delete(String id) {
		try {
			hibCtrl.deleteEntity(id);
			return true;
		} catch (Exception e) {
			LOGGER.error("Something went wrong while deleting: " + id);
			e.printStackTrace();
			return false;
		}
	}

}
