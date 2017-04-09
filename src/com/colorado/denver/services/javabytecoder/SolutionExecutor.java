package com.colorado.denver.services.javabytecoder;

import com.colorado.denver.model.Solution;

public class SolutionExecutor implements JavaExecutor {

	Solution sol;
	String inputType;
	String outputType;
	String excInput;
	String code;

	public SolutionExecutor(Solution sol) {
		this.sol = sol;
		this.inputType = sol.getExercise().getInputType();
		this.outputType = sol.getExercise().getOutputType();
		this.excInput = sol.getExercise().getInput();
		this.code = sol.getCode();

	}

	public String execute() {
		return executeJava(inputType, outputType, excInput, code);
	}

}
