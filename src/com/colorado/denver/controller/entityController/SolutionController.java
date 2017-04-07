package com.colorado.denver.controller.entityController;

import java.io.IOException;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.colorado.denver.model.Solution;
import com.colorado.denver.services.UserService;
import com.colorado.denver.tools.DenverConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class SolutionController extends ObjectOperationController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4974547446418316745L;
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SolutionController.class);

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Solution.SOLUTION, method = RequestMethod.POST)
	@ResponseBody
	public void handleSolutionRequest(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException, IOException {
		UserService.authorizeSystemuser();
		String jsonString = DenverConstants.ERROR;
		try {
			jsonString = super.checkRequest(request);
		} catch (JSONException e) {
			LOGGER.error("Error in OOC while handling the request: " + request.toString());
			e.printStackTrace();
		}

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		Solution entity = gson.fromJson(jsonString, Solution.class);
		String jsonResponse = super.doCrud(entity, jsonString);
		entity = null; // Let GC run over this quickly
		response.setStatus(200);
		response.getWriter().write(jsonResponse);
		response.getWriter().flush();
	}
}
