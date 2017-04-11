package com.colorado.denver.services.codeExecution;

import org.slf4j.LoggerFactory;

import com.colorado.denver.model.Exercise;
import com.colorado.denver.tools.DenverConstants;

public class ExerciseExecutor implements JavaExecutor, JavaScriptExecutor {
	final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExerciseExecutor.class);
	Exercise exc;
	String inputType;
	String outputType;
	String excInput;
	String code;

	public ExerciseExecutor(Exercise exc) {
		this.exc = exc;
		this.inputType = exc.getInputType();
		this.outputType = exc.getOutputType();
		this.excInput = exc.getInput();
		this.code = exc.getSolution_code();
	}

	public Exercise execute() {
		String answer = "";
		String message = "";
		if (exc.getLanguage().equals(DenverConstants.JAVA)) {
			LOGGER.info("Executing Java with code: " + code);
			answer = executeJava(inputType, outputType, excInput, code);
			if (answer.startsWith(DenverConstants.JAVA_EXCEPTION_THROWN)) {
				answer = DenverConstants.JAVA_EXCEPTION_THROWN;
				message = answer.replaceAll(DenverConstants.JAVA_EXCEPTION_THROWN, "");
			}
		} else {
			LOGGER.info("Executing JavaScript with code: " + code);
			answer = executeJavaScript(inputType, outputType, excInput, code);
		}
		exc.setAnwswer(answer);

		LOGGER.info("Answer for exercise " + exc.getId() + "  with value: " + answer);

		// Reset
		exc.setHasBeenModified(false);
		return exc;
	}

}
