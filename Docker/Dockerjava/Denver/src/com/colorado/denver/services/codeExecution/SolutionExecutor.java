package com.colorado.denver.services.codeExecution;

import org.slf4j.LoggerFactory;

import com.colorado.denver.model.Solution;
import com.colorado.denver.tools.DenverConstants;

public class SolutionExecutor implements JavaExecutor, JavaScriptExecutor {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SolutionExecutor.class);

	private Solution sol;
	private String[] excInput;
	private String code;
	private String entryMethod;

	public SolutionExecutor(Solution sol) {
		this.sol = sol;
		this.excInput = sol.getExercise().getInput();
		this.entryMethod = sol.getExercise().getEntryMethod();
		this.code = sol.getCode();

	}

	public Solution execute() {

		String answer = "";
		String message = "";
		if (sol.getExercise().getLanguage().equals(DenverConstants.JAVA)) {
			LOGGER.info("Executing Java");
			answer = executeJava(excInput, code, entryMethod);
			if (answer.startsWith(DenverConstants.JAVA_EXCEPTION_THROWN) || answer.startsWith(DenverConstants.EXC_THROWN)) {
				answer = DenverConstants.JAVA_EXCEPTION_THROWN;
				message = answer;
			}
		} else {
			LOGGER.info("Executing JavaScriptx");
			answer = executeJavaScript(excInput, code);
		}

		if (sol.getExercise().getAnswer().equals(answer)) {
			sol.setCorrect(true);
			LOGGER.info("Answer for entity " + sol.getId() + " correct with value: " + answer);
			message = DenverConstants.RESULT_CORRECT;
		} else {
			LOGGER.info("Answer for entity " + sol.getId() + " NOT correct with value: " + answer);
			message = DenverConstants.RESULT_WRONG;
			sol.setCorrect(false);
		}
		sol.setResult(answer);
		sol.setMessage(message);
		// Reset
		sol.setHasBeenModified(false);
		return sol;
	}

}
