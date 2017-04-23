package com.colorado.denver.services.security;

import java.util.Collection;

import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.colorado.denver.model.User;
import com.colorado.denver.services.user.UserService;

@Service
@Component("AuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	public Authentication authenticate(String username, String password) throws AuthenticationException {

		User user = UserService.getUserByLoginName(username);

		if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
			throw new BadCredentialsException("Username not found.");
		}

		if (!BCrypt.checkpw(password, user.getPassword())) {
			throw new BadCredentialsException("Wrong password.");
		}

		Collection<? extends GrantedAuthority> authorities = user.getPrivileges();
		LOGGER.info("Successful user login! " + user.getUsername());
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (authentication == null) {
			LOGGER.error("authentication null!");
		}
		try {
			String username = authentication.getName();
			String password = authentication.getCredentials().toString();

			User user = UserService.getUserByLoginName(username);

			if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
				throw new BadCredentialsException("Username not found." + user.getUsername() + " Name on authentication:" + username);
			}

			if (!BCrypt.checkpw(password, user.getPassword())) {
				throw new BadCredentialsException("Wrong password.");
			}

			Collection<? extends GrantedAuthority> authorities = user.getPrivileges();

			return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities);
		}

		catch (Exception e) {
			LOGGER.error("Error in authenticate!");
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}