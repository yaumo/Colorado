package com.colorado.denver.services.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.colorado.denver.services.SecurityServiceImpl;

public class SecurityFilter extends AbstractAuthenticationProcessingFilter {
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	AuthenticationSuccessHandler authenticationSuccessHandler;
	SecurityServiceImpl sec = new SecurityServiceImpl();

	public SecurityFilter() {
		super("/login");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		Authentication authentication;
		try {
			authentication = attemptAuthentication(request, response);
			if (authentication.isAuthenticated()) {
				successfulAuthentication(request, response, authentication);
			}
		} catch (AuthenticationException excep) {
			unsuccessfulAuthentication(request, response, excep);
		}
	}

	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authentication);
		authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		System.out.println(request.toString());
		System.out.println(request.getContentType());
		String username = getUsername(request);
		String password = getPassword(request);

		Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
		System.out.println(authentication);
		sec.authenticate(username, password);
		System.out.println("Usename:" + username);
		System.out.println("Password:" + password);

		return sec.authenticate(authentication);
	}

	public String getUsername(HttpServletRequest request) {
		return request.getParameter(USERNAME);
	}

	public String getPassword(HttpServletRequest request) {
		return request.getParameter(PASSWORD);
	}
}
