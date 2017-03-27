package com.colorado.denver.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.colorado.denver.model.BaseEntity;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.tools.DenverConstants;
import com.colorado.denver.tools.GenericTools;

@RestController
public class ObjectOperationController {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ObjectOperationController.class);

	public static void handleRequest(String id, int crud, Class<?> clazz, List<String> requestParamValues) {

		// find correct controller depending on 'clazz'

		// then call depending on CRUD:
		// 1: create: classXY = new ClassXY(); ---> THEN fire UPDATE(params)
		// 2: read: Call all getters on entity. Pack into JSON. Then response. MAYBE USE MAP?
		// 3: update: call all setters on entity. Get override existing values with params.
		// 4: delete: FUCK IT UP

		// JSON handling?

	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Exercise.EXERCISE, method = RequestMethod.POST)
	@ResponseBody
	public void handleExercise(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException, IOException {

		Class<? extends BaseEntity> clazz = GenericTools.getClassForName(request.getParameter(BaseEntity.OBJECT_CLASS));
		Objects.requireNonNull(clazz, "Object class must be provided!");
		LOGGER.debug("ObjectClass is: " + clazz.getSimpleName());

		String id = request.getParameter(BaseEntity.ID);
		int crud = Integer.parseInt(request.getParameter(DenverConstants.CRUD));
		List<String> requestParamValues = extractAllRequestPostParameters(request);

		for (int i = 0; i < requestParamValues.size(); i++) {
			System.out.println("Value at " + i + " : " + requestParamValues.get(i));
		}

		// get user via session
		// do security check
		/*
		 * Security dependencies:
		 * -Who is the User? Get user via the session!
		 * -Is the user allowed to use the CRUD operation in the request with the given Entity?
		 */

		handleRequest(id, crud, clazz, requestParamValues);

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
