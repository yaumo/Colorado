package com.colorado.denver.services.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3274089931622706782L;
	private final String principal;

	public UserAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String principal) {

		super(authorities);
		this.principal = principal;
	}

	public UserAuthenticationToken(Authentication auth) {
		this(auth.getAuthorities(), auth.getName());
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

}
