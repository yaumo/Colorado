package com.colorado.denver.manager;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.colorado.denver.model.DenverUser;
import com.colorado.denver.services.persistance.SessionTools;

public class UserManager {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserManager.class);

	public static DenverUser getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = getNameFromAuthentication(auth);
		return getUserByLoginName(name);
	}

	public static DenverUser getUserByLoginName(String loginName) {
		LOGGER.info("Trying to get user from DB: " + loginName);
		// we query Database directly. no use of chache!
		List<DenverUser> u = null;
		Session session = SessionTools.sessionFactory.getCurrentSession();
		u = session.createCriteria(DenverUser.class).setComment("getUser '" + loginName + "'")
				.add(Restrictions.eq(DenverUser.USERNAME, loginName).ignoreCase()).list();

		for (Iterator iterator = u.iterator(); iterator.hasNext();) {
			DenverUser denverUser = (DenverUser) iterator.next();
			LOGGER.info("Sucessfully received user from database: " + denverUser.getUsername());
		}
		return u.get(0);
	}

	private static String getNameFromAuthentication(Authentication auth) {
		return auth.getPrincipal().toString();
	}

}
