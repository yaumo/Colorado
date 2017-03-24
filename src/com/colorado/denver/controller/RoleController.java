package com.colorado.denver.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.colorado.denver.model.Role;
import com.colorado.denver.services.persistence.SessionTools;

public class RoleController {

	public static Role getRoleByName(String roleName) {
		Session session = SessionTools.sessionFactory.getCurrentSession();
		session.beginTransaction();// WTF??
		List<Role> role = session.createCriteria(Role.class).setComment("getRole '" + roleName + "'")
				.add(Restrictions.eq(Role.ROLE_NAME, roleName).ignoreCase()).list();
		return role.get(0);
	}
}
