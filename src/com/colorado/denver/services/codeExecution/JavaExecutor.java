package com.colorado.denver.services.codeExecution;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.slf4j.LoggerFactory;

import com.colorado.denver.tools.DenverConstants;

import groovy.lang.GroovyClassLoader;

public interface JavaExecutor {
	final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JavaExecutor.class);

	public default String executeJava(String inputType, String outputType, String excInput, String code) throws SecurityException {

		System.out.println("IN TEST!");

		String result = "";

		try {

			LOGGER.info(code);
			GroovyClassLoader gcl = new GroovyClassLoader();
			Class<?> scriptClass = gcl.parseClass(code);
			String className = scriptClass.getSimpleName();
			Object scriptInstance = scriptClass.newInstance();
			Method[] methods = scriptClass.getDeclaredMethods();
			Object[] params = new Object[1];

			int inputInt = 0;
			String inputString = "";
			boolean inputBoolean = false;
			Long inputLong = 0L;
			switch (inputType) {
			case "int":
				inputInt = Integer.parseInt(excInput);

				break;

			case "String":
				inputString = excInput;
				break;

			case "boolean":
				inputBoolean = Boolean.getBoolean(excInput);
				break;

			case "Long":
				inputLong = Long.parseLong(excInput);
				break;

			default:
				break;
			}

			for (int i = 0; i < methods.length; i++) {
				try {

					switch (methods[i].getReturnType().getSimpleName()) {
					case "int":

						int res = (int) methods[i].invoke(scriptInstance, new Object[] { inputInt });
						result = res + "";
						break;
					case "String":
						result = (String) methods[i].invoke(scriptInstance, new Object[] { inputString });
						break;

					case "boolean":

						boolean resultBool = (boolean) methods[i].invoke(scriptInstance, new Object[] { inputBoolean });
						if (resultBool) {
							result = "true";
						} else {
							result = "false";
						}

						break;

					case "long":
						long resultLong = (long) methods[i].invoke(scriptInstance, new Object[] { inputLong });
						result = resultLong + "";

						break;

					default:

					}
					System.out.println("Method name is: " + methods[i].getName());
					System.out.println("---");

				} catch (Exception e) {
					result = DenverConstants.JAVA_EXCEPTION_THROWN;
					result = result + "\n" + e.getMessage();
					e.printStackTrace();
				}

			}

			// int result = (int) scriptClass.getDeclaredMethod("fibonacci", int.class).invoke(scriptInstance, new Object[] { excInput });

			gcl.close();

			// CLeanup
			File fileTemp = new File(className + ".class");
			if (fileTemp.exists()) {
				fileTemp.delete();
			}

		} catch (

		InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Result is: " + result);
		return result;
	}
}
