package com.colorado.denver.controller.entityController;

import java.io.IOException;
import java.util.Set;

import javax.management.ReflectionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.services.ExerciseService;
import com.colorado.denver.services.UserService;
import com.colorado.denver.services.codeExecution.ExerciseExecutor;
import com.colorado.denver.tools.DenverConstants;
import com.colorado.denver.tools.Tools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping(value = "/exercise")
public class ExerciseController extends ObjectOperationController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6133518159703299316L;
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExerciseController.class);

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Exercise.EXERCISE, method = RequestMethod.POST)
	@ResponseBody
	public void handleExerciseRequest(HttpServletRequest request,
			HttpServletResponse response) throws ReflectionException, IOException {

		UserService.authorizeSystemuser();
		// JSONObject theObject = super.handleRequest(request, response);
		String jsonString = "";
		try {
			jsonString = super.checkRequest(request, DenverConstants.POST);
		} catch (JSONException e) {
			LOGGER.error("Error in OOC while handling the request: " + request.toString());
			e.printStackTrace();
		}

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		Gson gson = gb.create();

		Exercise entity = gson.fromJson(jsonString, Exercise.class);

		if (entity.isHasBeenModified()) {
			try {
				entity.setCode(Tools.unescape_string((entity.getCode())));
				entity.setSolution_code(Tools.unescape_string((entity.getSolution_code())));

				// Get real entity from db:
				HibernateController hibCtrl = new HibernateController();
				Exercise exc = (Exercise) hibCtrl.getEntity(entity.getHibId());
				ExerciseExecutor excExcutor = new ExerciseExecutor(exc);
				entity = excExcutor.execute();
				entity.setAnswer(exc.setAnswer());

				// Back to fronted:
				entity.setSolution_code(Tools.quote(entity.getSolution_code()));
			} catch (Exception e) {
				LOGGER.error("Executing Ecercise failed! : " + entity.getHibId());
				LOGGER.error("Executing Ecercise failed with code: " + entity.getSolution_code());
				e.printStackTrace();
			}

		}

		String jsonResponse = super.doOperation(entity, jsonString, DenverConstants.POST);
		entity = null; // Let GC run over this quickly, this entity is detached!
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setStatus(200);
		response.getWriter().write(jsonResponse);
		response.getWriter().flush();
		// response.getWriter().close(); // maybe no close because I didn't open this?
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Set<Exercise> getExersisesForUser() {
		return ExerciseService.getAllExercisesForUser(UserService.getCurrentUser().getHibId());
	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Exercise.EXERCISE, method = RequestMethod.PATCH)
	public Exercise exercisePatch(HttpServletRequest request, HttpServletResponse response) {
		UserService.authorizeSystemuser();

		String jsonString = "";

		try {
			jsonString = super.checkRequest(request, DenverConstants.PATCH);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ReflectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();

		Gson gson = gb.create();

		Exercise entity = gson.fromJson(jsonString, Exercise.class);

		String jsonResponse = super.doOperation(entity, jsonString, DenverConstants.PATCH);
		entity = null;
		Exercise entityAfterPatch = gson.fromJson(jsonResponse, Exercise.class);
		Link selfLink = linkTo(ExerciseController.class).withSelfRel();
		entityAfterPatch.add(selfLink);
		return entityAfterPatch;

	}

}
