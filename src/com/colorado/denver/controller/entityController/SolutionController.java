package com.colorado.denver.controller.entityController;

import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.model.Solution;
import com.colorado.denver.model.User;
import com.colorado.denver.services.ExerciseService;
import com.colorado.denver.services.SolutionService;
import com.colorado.denver.services.codeExecution.SolutionExecutor;
import com.colorado.denver.services.user.UserService;
import com.colorado.denver.tools.DenverConstants;
import com.colorado.denver.tools.GenericTools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@CrossOrigin
@RestController
// @RequestMapping(DenverConstants.FORWARD_SLASH + Solution.SOLUTION)
public class SolutionController extends ObjectOperationController {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SolutionController.class);

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Solution.SOLUTION, method = RequestMethod.PATCH)
	public Solution handleSolutionPatchRequest() {

		String jsonString = GenericTools.getRequestBody();

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		Solution entity = gson.fromJson(jsonString, Solution.class);
		if (!ExerciseService.dueDateReached(entity)) {
			try {
				entity.setCorrect(false); // init with false. Otherwise the value might be set from the frontend in another update
				entity.setExercise(ExerciseService.getExerciseForSolution(entity));
				SolutionExecutor solex = new SolutionExecutor(entity);

				entity = solex.execute();

			} catch (Exception e) {
				LOGGER.error("Executing Solution failed! : " + entity.getId());
				LOGGER.error("Executing Ecercise failed with code: " + entity.getCode());
				e.printStackTrace();
			}
		} else {
			return getSolutionForUser(entity.getId());
		}

		try {
			super.checkAccess(Solution.SOLUTION, DenverConstants.PATCH);
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
		}
		return (Solution) super.doDatabaseOperation(entity, DenverConstants.PATCH);
	}

	@RequestMapping(value = "/solution", method = RequestMethod.GET)
	public Solution getSolutionForUser(@RequestParam(value = "exeId", required = true) String exerId) {
		User usr = UserService.getCurrentUser();
		Set<Solution> sols = usr.getSolutions();
		for (Solution solution : sols) {
			if (solution.getExercise().getId().equals(exerId))
				return solution;
		}
		return null;
	}

	@RequestMapping(value = "/docent/solutions", method = RequestMethod.GET)
	public Set<Solution> getAllSolutions(@RequestParam(value = "lectureId", required = true) String lectureId,
			@RequestParam(value = "exerciseId", required = true) String exerciseId) {
		if (UserService.isCurrentUserDocent()) {
			return SolutionService.getAllSollutionsBasedCriteria(exerciseId, lectureId);

		} else {
			throw new AccessDeniedException("As a student you are not eligible to make this request!");
		}
	}

	@RequestMapping(value = "/docent/solution", method = RequestMethod.GET)
	public Solution getSolutionForUser(@RequestParam(value = "excId", required = true) String excId,
			@RequestParam(value = "usrId", required = false) String usrId) {

		if (usrId != null && UserService.isCurrentUserDocent()) {
			return SolutionService.getSollutionBasedCriteria(excId, usrId);
		} else {
			return SolutionService.getSollutionBasedCriteria(excId, UserService.getCurrentUser().getId());
		}

	}
}
