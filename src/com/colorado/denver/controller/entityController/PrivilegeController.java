package com.colorado.denver.controller.entityController;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.bind.annotation.RestController;

import com.colorado.denver.model.Privilege;
import com.colorado.denver.services.persistence.SessionTools;

@RestController
public class PrivilegeController extends ObjectOperationController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 218249683879368732L;

	public static Privilege getPrivilegeByName(String roleName) {
		Session session = SessionTools.sessionFactory.getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Privilege> role = session.createCriteria(Privilege.class).setComment("getPrivilege '" + roleName + "'")
				.add(Restrictions.eq(Privilege.PRIVILEGE_NAME, roleName).ignoreCase()).list();
		return role.get(0);
	}
}
