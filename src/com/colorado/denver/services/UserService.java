package com.colorado.denver.services;

import java.util.HashSet;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.colorado.denver.model.User;
import com.colorado.denver.services.persistence.SessionTools;
import com.colorado.denver.tools.DenverConstants;

@Service
public class UserService {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_GLOBAL_ADMINISTRATOR = "ROLE_GLOBAL_ADMINISTRATOR";

	public static User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			LOGGER.error("NO Authentifacation found!");
			return null;
		} else {
			String name = getNameFromAuthentication(auth);
			LOGGER.info("Getting User from DB: " + name);
			return getUserByLoginName(name);
		}

	}

	public static User authorizeSystemuser() {
		User user = getUserByLoginName(DenverConstants.SYSTEM);
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		// Authorize
		SecurityContextHolder.getContext().setAuthentication(auth);

		return user;
	}

	public static User getUserByLoginName(String loginName) {
		LOGGER.info("Trying to get user from DB: " + loginName);
		// we query Database directly. no use of chache!
		List<User> u = null;
		Session session = SessionTools.sessionFactory.getCurrentSession();
		session.beginTransaction();
		u = session.createCriteria(User.class).setComment("getUser '" + loginName + "'")
				.add(Restrictions.eq(User.USERNAME, loginName).ignoreCase()).list();

		LOGGER.info("Sucessfully received user from database: " + u.get(0).getUsername());
		return u.get(0);
	}

	private static String getNameFromAuthentication(Authentication auth) {
		return auth.getPrincipal().toString();
	}

	public void save(User user) {
		LOGGER.info("Security Save for user: " + user.getUsername() + " and password: " + user.getPassword());
		BCryptPasswordEncoder passWordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passWordEncoder.encode(user.getPassword()));
		user.setRoles(new HashSet<>());// TODO: Not
										// clean!

		// Hibernate save for user!
		LOGGER.info("Security Save for user: " + user.getUsername() + "sucessful! newPassword: " + user.getPassword());
	}

}