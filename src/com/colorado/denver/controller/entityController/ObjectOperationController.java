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
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.model.BaseEntity;
import com.colorado.denver.tools.DenverConstants;
import com.colorado.denver.tools.GenericTools;

public class ObjectOperationController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6726973624223302932L;
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ObjectOperationController.class);

	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response, Object callerObj) throws ReflectionException, IOException {
		// This Method should be generic!!
		// init
		JSONParser parser = new JSONParser();
		String objectClass = DenverConstants.ERROR_NO_OBJECT_FROM_REQUEST;
		String id = DenverConstants.ERROR_NO_ID_FROM_REQUEST;
		int crud = 0;// 0 is bad
		try {
			String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			Object obj = parser.parse(test);

			JSONObject jsonObject = (JSONObject) obj;
			System.out.println(jsonObject);
			objectClass = jsonObject.getString(BaseEntity.OBJECT_CLASS);
			crud = jsonObject.getInt(DenverConstants.CRUD);// Crud is not persisted. Therefore constant
			if (crud == 1) {
				id = DenverConstants.ID_CREATE_MODE;
			} else {
				id = jsonObject.getString(BaseEntity.ID);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Some Checks
		if (crud < 1 || crud > 4
				|| objectClass.equals(DenverConstants.ERROR_NO_OBJECT_FROM_REQUEST)
				|| id.equals(DenverConstants.ERROR_NO_ID_FROM_REQUEST)
				|| objectClass.equals(null)
				|| id.equals(null)) {
			LOGGER.error("Something is wrong with the Request in OOC Validation! Exiting...");
			throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
		}

		Class<? extends BaseEntity> clazz = GenericTools.getClassForName(objectClass);
		Objects.requireNonNull(clazz, "Model class was not found! for: " + objectClass);
		LOGGER.debug("ObjectClass is: " + clazz.getSimpleName());

		// get user via session
		// do security check
		/*
		 * Security dependencies:
		 * -Who is the User? Get user via the session!
		 * -Is the user allowed to use the CRUD operation in the request with the given Entity?
		 */

		// Controller experiments. Non productive code:...

		switch (crud) {
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;

		default:
			break;
		}

	}

	public static void handleRequest(String id, int crud, Class<?> clazz, List<String> requestParamValues) {

		// find correct controller depending on 'clazz'

		// then call depending on CRUD:
		// 1: create: classXY = new ClassXY(); ---> THEN fire UPDATE(params)
		// 2: read: Call all getters on entity. Pack into JSON. Then response. MAYBE USE MAP?
		// 3: update: call all setters on entity. Get override existing values with params.
		// 4: delete: FUCK IT UP

		// JSON handling?

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
