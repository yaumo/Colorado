package com.colorado.denver.controller.entityController;

import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.services.ExerciseService;
import com.colorado.denver.services.codeExecution.ExerciseExecutor;
import com.colorado.denver.services.user.UserService;
import com.colorado.denver.tools.DenverConstants;
import com.colorado.denver.tools.GenericTools;
import com.colorado.denver.tools.Tools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@CrossOrigin
@RestController
public class ExerciseController extends ObjectOperationController {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExerciseController.class);

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + DenverConstants.DOCENT + DenverConstants.FORWARD_SLASH
			+ Exercise.EXERCISE, method = RequestMethod.POST)
	@ResponseBody
	public Exercise handleExercisePostRequest() {
		String jsonString = GenericTools.getRequestBody();

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		Exercise entity = gson.fromJson(jsonString, Exercise.class);

		try {
			entity.setPatternSolution(Tools.unescape_string((entity.getPatternSolution())));

			// Get real entity from db:
			HibernateController hibCtrl = new HibernateController();
			Exercise exc = (Exercise) hibCtrl.getEntity(entity.getId());
			ExerciseExecutor excExcutor = new ExerciseExecutor(exc);
			entity = excExcutor.execute();
			entity.setAnswer(exc.getAnswer());

			// Back to fronted:
			entity.setPatternSolution(Tools.quote(entity.getPatternSolution()));
		} catch (Exception e) {
			LOGGER.error("Executing Ecercise failed! : " + entity.getId());
			LOGGER.error("Executing Ecercise failed with code: " + entity.getPatternSolution());
			e.printStackTrace();
		}

		try {
			super.checkAccess(Exercise.EXERCISE, DenverConstants.POST);
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
		}
		return (Exercise) super.doDatabaseOperation(entity, DenverConstants.POST);
	}

	@RequestMapping(value = "/exercise", method = RequestMethod.GET)
	public Exercise getExerciseForUser(@RequestParam(value = "exeId", required = true) String exeId) {
		Set<Exercise> exercises = ExerciseService.getAllExercisesForUser(UserService.getCurrentUser().getId());
		for (Exercise exercise : exercises) {
			if (exercise.getId().equals(exeId))
				return exercise;
		}

		return null;
	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + DenverConstants.DOCENT + "/exercises", method = RequestMethod.GET)
	public Set<Exercise> getAllExercisesForUser() {
		return ExerciseService.getAllExercisesForUser(UserService.getCurrentUser().getId());

	}

}
