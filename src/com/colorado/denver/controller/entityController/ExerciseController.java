package com.colorado.denver.controller.entityController;

import java.util.List;
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
			super.checkAccess(Exercise.EXERCISE, DenverConstants.POST);
			System.out.println("String before unescape: " + entity.getPatternSolution());
			entity.setPatternSolution(Tools.unescape_string((entity.getPatternSolution())));

			System.out.println("--------");
			System.out.println("String AFTER unescape: " + entity.getPatternSolution());
			ExerciseExecutor excExcutor = new ExerciseExecutor(entity);
			entity = excExcutor.execute();
			entity.setAnswer(entity.getAnswer());

			// Back to fronted:
			entity.setPatternSolution(Tools.quote(entity.getPatternSolution()));
			return (Exercise) super.doDatabaseOperation(entity, DenverConstants.POST);

		} catch (HttpServerErrorException e) {
			LOGGER.error("NOT AUTHORIZED!");
			e.printStackTrace();
		} catch (Exception e) {
			LOGGER.error("Executing Ecercise failed! : " + entity.getId());
			LOGGER.error("Executing Ecercise failed with code: " + entity.getPatternSolution());
			entity.setMessage(DenverConstants.ERROR);
			e.printStackTrace();
		}

		return entity;
	}

	@RequestMapping(value = "/exercise", method = RequestMethod.GET)
	public Exercise getExerciseForUser(@RequestParam(value = "exeId", required = true) String exeId) {
		HibernateController hibCtrl = new HibernateController();
		List<Exercise> list = (List<Exercise>) (List<?>) hibCtrl.getEntityList(Exercise.class);
		for (Exercise exercise : list) {
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
