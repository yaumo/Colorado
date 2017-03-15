package com.colorado.denver.controller;

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
import com.colorado.denver.model.Excercise;
import com.colorado.denver.tools.DenverConstants;
import com.colorado.denver.tools.GenericTools;

@RestController
public class ObjectOperationController {
	private static final Log4JLogger LOGGER = new Log4JLogger();

	public static void handleRequest(String id, int crud, Class<?> clazz) {

	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Excercise.EXCERCISE, method = RequestMethod.POST)
	@ResponseBody
	public Excercise handleExcercise(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException {

<<<<<<< HEAD
		//Class<? extends BaseEntity> c = request.getObjectClass();
		//Objects.requireNonNull(c, "Object class must be provided!");

		String id = request.getParameter("id");
		String crud = request.getParameter("crud");
		String objectClass = request.getParameter("objectClass");
=======
		Class<? extends BaseEntity> clazz = GenericTools.getClassForName(request.getParameter(BaseEntity.CLASS));
		Objects.requireNonNull(clazz, "Object class must be provided!");
		LOGGER.debug("ObjectClass is: " + clazz.getSimpleName());
		String id = request.getParameter(BaseEntity.ID);
		int crud = Integer.parseInt(request.getParameter(DenverConstants.CRUD));
>>>>>>> 2095a9d6faf8374ded27a854b73955075ccaa5bd
		String title = request.getParameter("title");

		handleRequest(id, crud, clazz);
		// get user via session
		// do security check
		Excercise excercise = null;

		HibernateController hibCtrl = new HibernateController();
		switch (crud) {
		case 1:
			String createdId = hibCtrl.addEntity(new Excercise(title));
			excercise = (Excercise) hibCtrl.getEntity(createdId, clazz);
			break;
		case 2:
			excercise = (Excercise) hibCtrl.getEntity(id, clazz);
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
}
