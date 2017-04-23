package com.colorado.denver.controller.entityController;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.colorado.denver.model.Privilege;
import com.colorado.denver.services.persistence.HibernateSession;

@CrossOrigin
@RestController
public class PrivilegeController extends ObjectOperationController {

	public static Privilege getPrivilegeByName(String roleName) {
		Session session = HibernateSession.sessionFactory.getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Privilege> role = session.createCriteria(Privilege.class).setComment("getPrivilege '" + roleName + "'")
				.add(Restrictions.eq(Privilege.PRIVILEGE_NAME, roleName).ignoreCase()).list();
		return role.get(0);
	}
}
