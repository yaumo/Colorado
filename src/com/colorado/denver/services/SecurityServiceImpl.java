package com.colorado.denver.services;

import java.util.Collection;

import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.colorado.denver.model.User;

@Service
@Component("AuthenticationProvider")
public class SecurityServiceImpl implements AuthenticationProvider {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);

	public Authentication authenticate(String username, String password) throws AuthenticationException {
		System.out.println("Starte Authentifizierung");
		// System.out.println(authentication);
		// System.out.println(authentication.getCredentials().toString());
		// String username = authentication.getName();
		// String password = authentication.getCredentials().toString();
		System.out.println("Username:" + username);

		User user = UserService.getUserByLoginName(username);

		if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
			throw new BadCredentialsException("Username not found.");
		}

		if (!password.equals(user.getPassword())) {
			throw new BadCredentialsException("Wrong password.");
		}

		Collection<? extends GrantedAuthority> authorities = user.getRoles();
		LOGGER.info("Successful user login! " + user.getUsername());
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (authentication == null) {
			LOGGER.error("authentication null!");
		}

		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		User user = UserService.getUserByLoginName(username);

		if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
			throw new BadCredentialsException("Username not found.");
		}

		if (!password.equals(user.getPassword())) {
			throw new BadCredentialsException("Wrong password.");
		}

		Collection<? extends GrantedAuthority> authorities = user.getRoles();
		LOGGER.info("Successful user login! " + user.getUsername());
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}