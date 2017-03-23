package com.colorado.denver.manager;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.colorado.denver.model.Role;
import com.colorado.denver.model.User;
import com.colorado.denver.services.persistence.SessionTools;
import com.colorado.denver.services.persistence.dao.RoleRepository;
import com.colorado.denver.services.persistence.dao.UserRepository;

@Service
@Transactional
public class UserManager {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserManager.class);

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

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

	public static User getUserByLoginName(String loginName) {
		LOGGER.info("Trying to get user from DB: " + loginName);
		// we query Database directly. no use of chache!
		List<User> u = null;
		Session session = SessionTools.sessionFactory.getCurrentSession();
		u = session.createCriteria(User.class).setComment("getUser '" + loginName + "'")
				.add(Restrictions.eq(User.USERNAME, loginName).ignoreCase()).list();

		for (Iterator iterator = u.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			LOGGER.info("Sucessfully received user from database: " + user.getUsername());
		}
		return u.get(0);
	}

	private static String getNameFromAuthentication(Authentication auth) {
		return auth.getPrincipal().toString();
	}

	public void save(User user) {
		LOGGER.info("Security Save for user: " + user.getUsername() + " and password: " + user.getPassword());
		BCryptPasswordEncoder passWordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passWordEncoder.encode(user.getPassword()));
		user.setRoles(new HashSet<>(roleRepository.findAll()));// TODO: Not clean!

		userRepository.save(user);
		LOGGER.info("Security Save for user: " + user.getUsername() + "sucessful! newPassword: " + user.getPassword());
	}

	public User findByUsername(String username) {
		return userRepository.findByusername(username);
	}

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByusername(username);

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Role role : user.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}

}
