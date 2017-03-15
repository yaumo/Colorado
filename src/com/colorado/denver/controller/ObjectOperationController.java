package com.colorado.denver.controller;

import java.util.Objects;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public static void handleRequest(String id, int crud, Class<?> clazz) {

	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Excercise.EXCERCISE, method = RequestMethod.POST)
	@ResponseBody
	public Excercise handleExcercise(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException {

		//Class<? extends BaseEntity> c = request.getObjectClass();
		//Objects.requireNonNull(c, "Object class must be provided!");

		String id = request.getParameter("id");
		String crud = request.getParameter("crud");
		String objectClass = request.getParameter("objectClass");
		String title = request.getParameter("title");
		Class<?> clazz = GenericTools.getClassForName(objectClass);

		handleRequest(id, Integer.parseInt(crud), clazz);
		// get user via session
		// do security check
		Excercise excercise = null;

		int intCrud = Integer.parseInt(crud);
		HibernateController hibCtrl = new HibernateController();
		switch (intCrud) {
		case 1:
			String createdId = hibCtrl.addEntity(new Excercise(title));
			excercise = (Excercise) hibCtrl.getEntity(createdId, Excercise.class);
			break;
		case 2:
			excercise = (Excercise) hibCtrl.getEntity(id, Excercise.class);
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
