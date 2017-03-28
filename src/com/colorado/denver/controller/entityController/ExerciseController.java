package com.colorado.denver.controller.entityController;

import java.io.IOException;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.services.persistence.HibernateGeneralTools;
import com.colorado.denver.tools.DenverConstants;

@RestController
public class ExerciseController extends ObjectOperationController {

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Exercise.EXERCISE, method = RequestMethod.POST)
	@ResponseBody
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException, IOException {
		super.handleRequest(request, response, this);
	}

	private static Exercise create(Exercise clazz) {
		HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();
		Exercise exc = new Exercise();
		hibCtrl.addEntity(exc);
		ExerciseController.update();
		return exc;
	}

	private static Exercise read() {
		// do stuff
		return null;
	}

	private static Exercise update() {
		// do stuff
		return null;
	}

	private static boolean delete() {
		// do stuff
		return false;
	}
}
