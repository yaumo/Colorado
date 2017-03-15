package com.colorado.denver.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;
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
	private static final Log4JLogger LOGGER = new Log4JLogger();

	public static void handleRequest(String id, int crud, Class<?> clazz) {

	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Exercise.EXERCISE, method = RequestMethod.POST)
	@ResponseBody
	public Exercise handleExercise(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException, IOException {

		Class<? extends BaseEntity> clazz = GenericTools.getClassForName(request.getParameter(BaseEntity.CLASS));
		Objects.requireNonNull(clazz, "Object class must be provided!");

		LOGGER.debug("ObjectClass is: " + clazz.getSimpleName());
		String id = request.getParameter(BaseEntity.ID);
		int crud = Integer.parseInt(request.getParameter(DenverConstants.CRUD));
		String[] requestParams = extractAllRequestPostParameters(request);

		for (int i = 0; i < requestParams.length; i++) {
			System.out.println("Value at " + i + " : " + requestParams[i]);
		}

		handleRequest(id, crud, clazz);
		// get user via session
		// do security check
		Exercise excercise = null;
		HibernateController hibCtrl = new HibernateController();
		switch (crud) {
		case 1:
			String createdId = hibCtrl.addEntity(new Exercise("HardCodedTest"));
			excercise = (Exercise) hibCtrl.getEntity(createdId, clazz);
			break;
		case 2:
			excercise = (Exercise) hibCtrl.getEntity(id, clazz);
			break;
		case 3:

			break;
		case 4:

			break;

		default:
			break;
		}

		return excercise;
	}

	public static String[] extractAllRequestPostParameters(HttpServletRequest req) throws IOException {
		Enumeration<String> parameterNames = req.getParameterNames();
		String paramName = null;
		while (parameterNames.hasMoreElements()) {
			paramName = parameterNames.nextElement();
		}
		return req.getParameterValues(paramName);
	}
}
