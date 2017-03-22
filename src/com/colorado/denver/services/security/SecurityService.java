package com.colorado.denver.services.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import com.colorado.denver.model.BaseEntity;
import com.colorado.denver.model.DenverUser;

public class SecurityService<SCOPE extends BaseEntity<?>> {

	public static final String ROLE_TEAM_MEMBER = "AUTHORITY_TEAM_MEMBER";
	public static final String ROLE_GLOBAL_ADMINISTRATOR = "AUTHORITY_GLOBAL_ADMINISTRATOR";

	public static void authorizeUser(DenverUser user) {
		// authenticate if nobody is logged in or we are currently logged in with a different user name
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) {
			// Need to create a token
			AbstractAuthenticationToken token = new DenverUserAuthenticationToken(user);
			SecurityContextHolder.getContext().setAuthentication(token);
			Assert.state(SecurityContextHolder.getContext().getAuthentication().isAuthenticated(), "Authentication failed");
		}
	}

}
