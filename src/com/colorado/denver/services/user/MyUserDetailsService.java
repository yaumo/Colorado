package com.colorado.denver.services.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.colorado.denver.model.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) {
		System.out.println("Login getting user: " + username);
		User user = UserService.getUserByLoginName(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		System.out.println("Returning User:" + user.getUsername());

		return new MyUserPrincipal(user);
	}

}