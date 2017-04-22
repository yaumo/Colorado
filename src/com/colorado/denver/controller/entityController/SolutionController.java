package com.colorado.denver.controller.entityController;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Solution;
import com.colorado.denver.model.User;
import com.colorado.denver.services.SolutionService;
import com.colorado.denver.services.codeExecution.SolutionExecutor;
import com.colorado.denver.services.user.UserService;
import com.colorado.denver.tools.DenverConstants;
import com.colorado.denver.tools.GenericTools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
// @RequestMapping(DenverConstants.FORWARD_SLASH + Solution.SOLUTION)
public class SolutionController extends ObjectOperationController {

	private static final long serialVersionUID = 4974547446418316745L;
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SolutionController.class);

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Solution.SOLUTION, method = RequestMethod.POST)
	public Solution handleSolutionPostRequest() {

		String jsonString = GenericTools.getRequestBody();

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		Solution entity = gson.fromJson(jsonString, Solution.class);

		if (entity.isHasBeenModified()) {
			try {
				entity.setCorrect(false); // init with false. Otherwise the value might be set from the frontend in another update
				// Experimental! We need the code from the itself not from a file on the server
				InputStream is = new FileInputStream("addition.txt");
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

		try {
			super.checkAccess(Solution.SOLUTION, DenverConstants.POST);
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
		}
		return (Solution) super.doDatabaseOperation(entity, DenverConstants.POST);
	}

	@RequestMapping(value = DenverConstants.FORWARD_SLASH + Solution.SOLUTION, method = RequestMethod.PATCH)
	public Solution handleSolutionPatchRequest() {

		String jsonString = GenericTools.getRequestBody();

		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		gb.serializeNulls();
		Gson gson = gb.create();

		Solution entity = gson.fromJson(jsonString, Solution.class);

		if (entity.isHasBeenModified()) {
			try {
				entity.setCorrect(false); // init with false. Otherwise the value might be set from the frontend in another update
				// Experimental! We need the code from the itself not from a file on the server
				InputStream is = new FileInputStream("addition.txt");
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

		try {
			super.checkAccess(Solution.SOLUTION, DenverConstants.PATCH);
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
		}
		return (Solution) super.doDatabaseOperation(entity, DenverConstants.PATCH);
	}

	@RequestMapping(value = "/solution", method = RequestMethod.GET)
	public Solution getSolutionForUser(@RequestParam(value = "solId", required = true) String solId) {
		User usr = UserService.getCurrentUser();
		Set<Solution> sols = usr.getSolutions();

		for (Iterator iterator = sols.iterator(); iterator.hasNext();) {
			Solution solution = (Solution) iterator.next();
			if (solution.getHibId().equals(solId))
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
			return SolutionService.getSollutionBasedCriteria(excId, UserService.getCurrentUser().getHibId());
		}

	}
}
