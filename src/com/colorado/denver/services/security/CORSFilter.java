package com.colorado.denver.services.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CORSFilter implements Filter {

	private final List<String> allowedOrigins = Arrays.asList("http://localhost:8080", "http://localhost:8081");

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CORSFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		// Lets make sure that we are working with HTTP (that is, against HttpServletRequest and HttpServletResponse objects)
		if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;

			String origin = request.getHeader("Origin");
			response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
			response.setHeader("Vary", "Origin");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Authorization, *");
		}
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}
}