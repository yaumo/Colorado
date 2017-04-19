package com.colorado.denver.controller.entityController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Solution;
import com.colorado.denver.model.User;
import com.colorado.denver.services.UserService;
import com.colorado.denver.services.codeExecution.SolutionExecutor;
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
			jsonString = super.checkRequest(request, DenverConstants.POST, Solution.SOLUTION);// Add request Method from DenverConstants
		} catch (JSONException e) {
			LOGGER.error("Error in OOC while handling the request: " + request.toString());
			e.printStackTrace();
		} catch (HttpServerErrorException e) {
			LOGGER.error("Not authorized to handle request:" + request.toString());
			e.printStackTrace();
			response.setStatus(403);
		}

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();
		Solution entity = gson.fromJson(jsonString, Solution.class);

		if (entity.isSubmitted() && entity.isHasBeenModified()) {
			try {
				entity.setCorrect(false); // init with false. Otherwise the value might be set from the frontend in another update
				// Experimental! We need the code from the itself not from a file on the server
				InputStream is = new FileInputStream("fibonacci.txt");
				BufferedReader buf = new BufferedReader(new InputStreamReader(is));
				String line = buf.readLine();
				StringBuilder sb = new StringBuilder();

				while (line != null) {
					sb.append(line).append("\n");
					line = buf.readLine();
				}
				buf.close();

				String fileAsString = sb.toString();
				// experiment

				// Set real Exercise Entity on SOlution NOT the detached one from the Frontend! -> Prevent cheating by user
				HibernateController hibCtrl = new HibernateController();
				Solution sol = (Solution) hibCtrl.getEntity(entity.getHibId());

				// Exercise exc = (Exercise) hibCtrl.getEntity(entity.getExercise().getHibId());
				entity.setExercise(sol.getExercise());

				entity.setCode(fileAsString);
				SolutionExecutor solex = new SolutionExecutor(entity);

				entity = solex.execute();

			} catch (Exception e) {
				LOGGER.error("Executing Solution failed! : " + entity.getId());
				LOGGER.error("Executing Ecercise failed with code: " + entity.getCode());
				e.printStackTrace();
			}

		}

		String jsonResponse = super.doOperation(entity, jsonString, DenverConstants.POST);
		// entity = gson.fromJson(jsonResponse, Solution.class);

		entity = null; // Let GC run over this quickly
		response.setStatus(200);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().write(jsonResponse);
		response.getWriter().flush();
	}

	@RequestMapping(value = "/solution", method = RequestMethod.GET)
	public Set<Solution> getSolutionsForUser(@RequestParam(value = "excId", required = false) String excId,
			@RequestParam(value = "usrId", required = false) String usrId) {
		User usr = UserService.getCurrentUser();
		Set<Solution> sols = new HashSet<>();
		LOGGER.info("UserId:" + usrId);
		LOGGER.info("ExcId:" + excId);

		if (usrId != null) {
			// Current user Docent?
			User student = UserService.getUserById(usrId);
			sols = student.getSolutions();
			return sols;

		} else {
			sols = usr.getSolutions();
		}

		return sols;
	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Solution.SOLUTION, method = RequestMethod.PATCH)
	public Solution solutionPatch(HttpServletRequest request, HttpServletResponse response) throws ReflectionException, IOException {
		UserService.authorizeSystemuser();

		String jsonString = "";
		try {
			jsonString = super.checkRequest(request, DenverConstants.PATCH, Solution.SOLUTION);
		} catch (JSONException e) {
			LOGGER.error("Error in OOC while handling the request:" + request.toString());
			e.printStackTrace();
		} catch (HttpServerErrorException e) {
			LOGGER.error("Not authorized to handle request:" + request.toString());
			e.printStackTrace();
			response.setStatus(403);
		}

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		Solution entity = gson.fromJson(jsonString, Solution.class);

		String jsonResponse = super.doOperation(entity, jsonString, DenverConstants.PATCH);
		entity = null;
		Solution entityAfterPatch = gson.fromJson(jsonResponse, Solution.class);
		Link selfLink = linkTo(SolutionController.class).withSelfRel();
		entityAfterPatch.add(selfLink);
		return entityAfterPatch;
	}
}
