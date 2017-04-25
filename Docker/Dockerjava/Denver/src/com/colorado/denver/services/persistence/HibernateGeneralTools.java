package com.colorado.denver.services.persistence;

import com.colorado.denver.controller.HibernateController;

public class HibernateGeneralTools {

	private static final HibernateController hibCtrl = new HibernateController();

	public static HibernateController getHibernateController() {
		return hibCtrl;
	}

}
