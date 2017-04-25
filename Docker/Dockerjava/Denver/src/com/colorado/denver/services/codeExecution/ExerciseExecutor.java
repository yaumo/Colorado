package com.colorado.denver.services.codeExecution;

import org.slf4j.LoggerFactory;

import com.colorado.denver.model.Exercise;
import com.colorado.denver.tools.DenverConstants;

public class ExerciseExecutor implements JavaExecutor, JavaScriptExecutor {
	final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExerciseExecutor.class);
	private Exercise exc;
	private String[] excInput;
	private String code;
	private String entryMethod;

	public ExerciseExecutor(Exercise exc) {
		this.exc = exc;
		this.excInput = exc.getInput();
		this.code = exc.getPatternSolution();
		this.entryMethod = exc.getEntryMethod();
	}

	public Exercise execute() {
		String answer;
		String message = "Code executed sucessfully!";
		if (exc.getLanguage().equals(DenverConstants.JAVA)) {
			LOGGER.info("Executing Java code");
			answer = executeJava(excInput, code, entryMethod);
			if (answer.startsWith(DenverConstants.JAVA_EXCEPTION_THROWN) || answer.startsWith(DenverConstants.EXC_THROWN)) {
				message = answer;
			}
		} else {
			LOGGER.info("Executing JavaScript with code: " + code);
			answer = executeJavaScript(excInput, code, entryMethod);
			if (answer.startsWith(DenverConstants.EXC_THROWN)) {
				message = answer;
			}
		}
		exc.setAnswer(answer);
		exc.setMessage(message);
		LOGGER.info("Answer for exercise " + exc.getId() + "  with value: " + answer);

		// Reset
		exc.setHasBeenModified(false);
		return exc;
	}

}
