package com.colorado.denver.services.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.controller.entityController.PrivilegeController;
import com.colorado.denver.model.Privilege;
import com.colorado.denver.model.User;
import com.colorado.denver.services.persistence.SessionTools;
import com.colorado.denver.tools.DenverConstants;

@Service
public class UserService {

	BCryptPasswordEncoder passWordEncoder = new BCryptPasswordEncoder();

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	public static final String ROLE_STUDENT = "ROLE_STUDENT";
	public static final String ROLE_TUTOR = "ROLE_TUTOR";
	public static final String ROLE_DOCENT = "ROLE_DOCENT";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";

	public static User getCurrentUser() {
		// Find out username and retrieve the user from DB
		// We do NOT use the user object returned by the Token!!!!s

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			LOGGER.error("NO Authentification found!");
			return null;
		} else {

			String name = getLoginNameFromAuthentication(auth);
			LOGGER.info("Getting Current User from DB: " + name);
			return getUserByLoginName(name);
		}

	}

	public static UsernamePasswordAuthenticationToken authorizeSystemuser() {
		Privilege role = PrivilegeController.getPrivilegeByName(ROLE_ADMIN);
		List<Privilege> roles = new ArrayList<>();
		roles.add(role);
		return authorizeUserByLoginName(DenverConstants.SYSTEM, "password", roles);
	}

	public static UsernamePasswordAuthenticationToken authorizeUserByLoginName(String username, String password, Collection<Privilege> roles) {

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, roles);

		SecurityContext secContext = createNewSecurityContext(authToken);
		SecurityContextHolder.setContext(secContext);

		return authToken;
	}

	public static SecurityContext createNewSecurityContext(UsernamePasswordAuthenticationToken auth) {
		SecurityContext context = new SecurityContextImpl();
		context.setAuthentication(auth);
		return context;
	}

	public static User getUserByLoginName(String loginName) {
		// we query Database directly. no use of chache!
		List<User> u = null;
		Session session = SessionTools.sessionFactory.openSession();
		session.beginTransaction();
		u = session.createCriteria(User.class).setComment("getUser '" + loginName + "'")
				.add(Restrictions.eq(User.USERNAME, loginName).ignoreCase()).list();
		session.flush();
		session.close();
		if (!u.isEmpty()) {
			return u.get(0);
		} else {
			LOGGER.error("No user found for the given name: " + loginName);
			return null;
		}

	}

	public static String getLoginNameFromAuthentication(Authentication auth) {
		LOGGER.info("Principal on auth is: " + auth.getPrincipal().toString());

		Object princ = auth.getPrincipal();
		if (princ instanceof MyUserPrincipal) {
			MyUserPrincipal myPrinc = (MyUserPrincipal) princ;
			return myPrinc.getUsername();
		} else {
			LOGGER.error("Wrong Principal is used to get User?!!!" + auth.toString());
			return princ.toString();
		}

	}

	public void save(User user) {
		LOGGER.info("Security Save for user: " + user.getUsername() + " and password: " + user.getPassword());
		user.setPassword(passWordEncoder.encode(user.getPassword()));
		user.setPrivileges(new HashSet<>());// TODO: Not
		// clean!

		// Hibernate save for user!
		LOGGER.info("Security Save for user: " + user.getUsername() + "sucessful! newPassword: " + user.getPassword());

	}

	public static List<User> allUsers() {
		LOGGER.info("I AM GETTING ALL USERS");
		HibernateController hibCtrl = new HibernateController();

		return (List<User>) (List<?>) hibCtrl.getEntityList(User.class);

	}

	public static User getUserById(String id) {
		HibernateController hibCtrl = new HibernateController();
		User usr = (User) hibCtrl.getEntity(id);
		return usr;
	}

	public static boolean isCurrentUserDocent() {

		try {
			User currentUser = getCurrentUser();
			Collection<Privilege> roles = currentUser.getPrivileges();
			Iterator<Privilege> iterator = roles.iterator();

			while (iterator.hasNext()) {
				if (iterator.next().getPrivilegeName().equals("ROLE_DOCENT")) {
					return true;
				}
			}

			return false;
		} catch (Exception e) {
			LOGGER.error("Error evaluating if current User is Docent!");
			LOGGER.error(SecurityContextHolder.getContext().getAuthentication().toString());
			e.printStackTrace();
			return false;
		}

	}

}