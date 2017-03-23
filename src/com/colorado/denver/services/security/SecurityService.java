package com.colorado.denver.services.security;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.colorado.denver.DenverApplication;

@Service
@Component
public class SecurityService {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DenverApplication.class);
	public static final String ROLE_TEAM_MEMBER = "AUTHORITY_TEAM_MEMBER";
	public static final String ROLE_GLOBAL_ADMINISTRATOR = "AUTHORITY_GLOBAL_ADMINISTRATOR";

	/*
	 * public static void authorizeUser(User user) {
	 * // authenticate if nobody is logged in or we are currently logged in with a different user name
	 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	 * 
	 * if (auth == null) {
	 * // Need to create a token
	 * AbstractAuthenticationToken token = new UserAuthenticationToken(user);
	 * SecurityContextHolder.getContext().setAuthentication(token);
	 * Assert.state(SecurityContextHolder.getContext().getAuthentication().isAuthenticated(), "Authentication failed");
	 * }
	 * }
	 */
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	public String returnLoggedInUserName() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails).getUsername();
		}

		return null;
	}

	public void autologin(String username, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password,
				userDetails.getAuthorities());

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			LOGGER.debug(String.format("Auto login %s successfully!", username));
		}
	}

}
