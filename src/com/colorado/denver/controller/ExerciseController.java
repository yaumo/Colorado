package com.colorado.denver.controller;

import com.colorado.denver.model.Exercise;
import com.colorado.denver.services.persistance.HibernateGeneralTools;

public class ExerciseController {
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
