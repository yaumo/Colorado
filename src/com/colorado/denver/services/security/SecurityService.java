package com.colorado.denver.services.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import com.colorado.denver.model.DenverUser;

public class SecurityService {

	public static void authorizeUser(DenverUser user) {
		// authenticate if nobody is logged in or we are currently logged in with a different user name
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) {
			AbstractAuthenticationToken token = new DenverUserAuthenticationToken(user);
			SecurityContextHolder.getContext().setAuthentication(token);
			Assert.state(SecurityContextHolder.getContext().getAuthentication().isAuthenticated(), "Authentication failed");
		}
	}

}
