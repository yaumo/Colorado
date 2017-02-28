package com.colorado.denver.services.javabytecoder;

public class Executor {

	class JavaToExecute {
		public String returnTest() {
			return null;
		}
	}

	public String executeJava() {
		/*
		 * JavaToExecute clazz = new ByteBuddy().subclass(Executor.class)
		 * .method(named("returnTest"))
		 * .intercept(FixedValue.value("Two!")
		 * .make()
		 * .load(getClass().getClassLoader())
		 * .getLoaded()
		 * .newInstance();
		 */
		return "ERROR";
	}

}
