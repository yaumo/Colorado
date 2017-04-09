package com.colorado.denver.services.javabytecoder;

import com.colorado.denver.model.Exercise;

public class ExerciseExecutor implements JavaExecutor {

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
		this.code = exc.getCode();
	}

}
