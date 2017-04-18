package com.colorado.denver.controller.entityController;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.BaseEntity;
import com.colorado.denver.model.Course;
import com.colorado.denver.model.EducationEntity;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.model.Lecture;
import com.colorado.denver.model.Role;
import com.colorado.denver.model.Solution;
import com.colorado.denver.model.User;
import com.colorado.denver.services.persistence.HibernateGeneralTools;
import com.colorado.denver.tools.DenverConstants;
import com.colorado.denver.tools.GraphAdapterBuilder;
import com.colorado.denver.view.GsonExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ObjectOperationController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6726973624223302932L;
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ObjectOperationController.class);
	private HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();

	private User workingUser = null;

	public String checkRequest(HttpServletRequest request, String mode) throws ReflectionException, IOException, JSONException {
		// This Method should be generic!!
		// init

		String objectClass = DenverConstants.ERROR_NO_OBJECT_FROM_REQUEST;
		String id = DenverConstants.ERROR_NO_ID_FROM_REQUEST;
		JSONObject jsonObject = null;

		String jsonStr = DenverConstants.ERROR_NO_OBJECT_FROM_REQUEST;
		try {
			jsonStr = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			// Object obj = parser.parse(jsonStr);

			// JSON validation
			if (jsonStr.contains("0x1")) {

				LOGGER.error("0x1 on parent!!" + jsonStr);
				throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
			}
			jsonObject = new JSONObject(jsonStr);

			jsonObject.put(DenverConstants.JSON, jsonStr);// we need this back

			objectClass = jsonObject.getString(BaseEntity.OBJECT_CLASS);

			switch (mode) {
			case "POST":
				id = DenverConstants.ID_CREATE_MODE;
				break;

			default:
				id = jsonObject.getString(BaseEntity.HIB_ID);
				break;
			}
			// jsonObject.("id", id);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Some Checks
		if (objectClass.equals(DenverConstants.ERROR_NO_OBJECT_FROM_REQUEST)
				|| id.equals(DenverConstants.ERROR_NO_ID_FROM_REQUEST)
				|| objectClass.equals(null)
				|| id.equals(null)) {
			LOGGER.error("Something is wrong with the Request in OOC Validation! ID: " + id + " | objectClass: " + objectClass);
			jsonObject = null;// Invalidate the request for further handling
			throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
		}

		// TODO: Security checks here Nele!! Based on current user (You can parse via method declaration and the operation and entity!
		// Write user in "workingUser", see class at top
		// do security check
		/*
		 * Security dependencies:
		 * -Who is the User? Get user via the session!
		 * -Is the user allowed to use the operation in the request with the given Entity?
		 */
		return jsonObject.getString(DenverConstants.JSON);

	}

	public String doOperation(BaseEntity<?> entity, String jsonString, String mode) {

		// init
		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.setExclusionStrategies(new GsonExclusionStrategy());
		gb.serializeNulls();

		new GraphAdapterBuilder()
				.addType(Exercise.class)
				.addType(Solution.class)
				.addType(Lecture.class)
				.addType(Course.class)
				.addType(Role.class)
				.addType(User.class)
				.registerOn(gb);
		Gson gson = gb.create();

		String id = DenverConstants.ERROR;
		try {
			id = entity.getHibId();

		} catch (Exception e) {
			LOGGER.error("Error extraxting information from the Object(FromJSON): " + entity.getId());
			e.printStackTrace();
		}

		String jsonResponse = DenverConstants.ERROR;
		switch (mode) {
		case "POST":
			String newId = create(entity);
			entity.setHibId(newId);
			jsonResponse = gson.toJson(read(newId));
			break;
		case "PATCH":
			jsonResponse = gson.toJson(update(entity));
			break;
		case "DELETE":
			delete(id);
			jsonResponse = "SUCCESS";
			break;

		default:
			LOGGER.error("ERROR IN ASSERTING A OPERTAION!! VALUE: " + mode);
			throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);// last resort
		}
		workingUser = null;
		return jsonResponse;
	}

	private String create(BaseEntity<?> entity) {
		if (entity instanceof EducationEntity) {
			((EducationEntity) entity).setOwner(workingUser);
		}

		String id = hibCtrl.addEntity(entity);
		return id;
	}

	private BaseEntity<?> read(String id) {
		return hibCtrl.getEntity(id);
	}

	private List<BaseEntity<?>> read(BaseEntity<?> entities) {
		return hibCtrl.getEntityList(entities.getClass());
	}

	private BaseEntity<?> update(BaseEntity<?> entity) {
		if (entity instanceof EducationEntity) {
			((EducationEntity) entity).setOwner(workingUser);
		}

		try {
			return hibCtrl.mergeEntity(entity);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("MERGE failed! Trying update");

			return hibCtrl.updateEntity(entity, entity.getHibId());
		}

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

	@SuppressWarnings("null")
	public static List<String> extractAllRequestPostParameters(HttpServletRequest req) throws IOException {
		Enumeration<String> parameterNames = req.getParameterNames();
		List<String> paramValues = null;

		while (parameterNames.hasMoreElements()) {
			paramValues.add(req.getParameter(parameterNames.nextElement()));
		}
		return paramValues;
	}
}
