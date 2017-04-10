package com.colorado.denver.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.model.User;
import com.colorado.denver.services.SecurityServiceImpl;
import com.colorado.denver.tools.DenverConstants;

@RestController
public class LoginController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5162423220413438255L;

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + DenverConstants.LOGIN, method = RequestMethod.POST)
	@ResponseBody
	public void handleLoginRequest(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException {
		JSONObject jsonObject = null;
		System.out.println("HALLO bin im login!");
		String jsonStr = DenverConstants.ERROR_NO_OBJECT_FROM_REQUEST;
		String username = "";
		String password = "";
		try {
			jsonStr = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			jsonObject = new JSONObject(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
		}

		try {
			username = jsonObject.getString(User.USERNAME);
			password = jsonObject.getString(User.PASSWORD);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(username);
		System.out.println(password);
		SecurityServiceImpl impl = new SecurityServiceImpl();

		impl.authenticate(authentication);
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
		response.setStatus(200);
		response.getWriter().write(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
		response.getWriter().flush();
	}

}
