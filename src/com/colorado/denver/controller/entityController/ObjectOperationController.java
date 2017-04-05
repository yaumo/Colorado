package com.colorado.denver.controller.entityController;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.BaseEntity;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.services.persistence.HibernateGeneralTools;
import com.colorado.denver.tools.DenverConstants;
import com.colorado.denver.tools.GenericTools;
import com.google.gson.GsonBuilder;

public class ObjectOperationController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6726973624223302932L;
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ObjectOperationController.class);
	private HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();

	public JSONObject handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException, IOException {
		// This Method should be generic!!
		// init
		JSONParser parser = new JSONParser();
		String objectClass = DenverConstants.ERROR_NO_OBJECT_FROM_REQUEST;
		String id = DenverConstants.ERROR_NO_ID_FROM_REQUEST;
		int crud = 0;// 0 is bad
		JSONObject jsonObject = null;

		GsonBuilder builder = new GsonBuilder();
		String jsonStr = DenverConstants.ERROR_NO_OBJECT_FROM_REQUEST;
		try {
			jsonStr = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			// Object obj = parser.parse(jsonStr);

			jsonObject = new JSONObject(jsonStr);
			jsonObject.put(DenverConstants.JSON, jsonStr);// we need this back

			objectClass = jsonObject.getString(BaseEntity.OBJECT_CLASS);

			crud = jsonObject.getInt(DenverConstants.CRUD);// Crud is not persisted. Therefore constant
			if (crud == 1) {
				id = DenverConstants.ID_CREATE_MODE;
			} else {
				id = jsonObject.getString(BaseEntity.ID);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Some Checks
		if (crud < 1 || crud > 4
				|| objectClass.equals(DenverConstants.ERROR_NO_OBJECT_FROM_REQUEST)
				|| id.equals(DenverConstants.ERROR_NO_ID_FROM_REQUEST)
				|| objectClass.equals(null)
				|| id.equals(null)) {
			LOGGER.error("Something is wrong with the Request in OOC Validation! ID: " + id + " | objectClass: " + objectClass);
			jsonObject = null;// Invalidate the request for further handling
			throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
		}

		Class<? extends BaseEntity<?>> clazz = GenericTools.getModelClassForName(objectClass);
		Objects.requireNonNull(clazz, "Model class was not found! for: " + objectClass);
		LOGGER.debug("ObjectClass is: " + clazz.getSimpleName());

		// get user via session
		// do security check
		/*
		 * Security dependencies:
		 * -Who is the User? Get user via the session!
		 * -Is the user allowed to use the CRUD operation in the request with the given Entity?
		 */

		return jsonObject;

	}

	private BaseEntity<?> update(BaseEntity<?> entity) {
		return hibCtrl.mergeEntity(entity);
	}

	private String create() {
		Exercise exc = new Exercise();
		String id = hibCtrl.addEntity(exc);
		return id;
	}

	private BaseEntity<?> read(String id) {
		return hibCtrl.getEntity(id);
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
