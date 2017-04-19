package com.colorado.denver.services.codeExecution;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.slf4j.LoggerFactory;

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
			Object[] params = null;
			int[] paramsInt = null;

			int inputInt = -7864;
			String inputString = "";
			boolean inputBoolean = false;
			Long inputLong = -179561792L;
			switch (inputType) {
			case "int":
				inputInt = Integer.parseInt(excInput);
				break;

			case "String":
				// params[0] = excInput;
				break;

			case "boolean":
				inputBoolean = Boolean.getBoolean(excInput);
				// params[0] = inputBoolean;
				break;

			case "Long":
				inputLong = Long.parseLong(excInput);
				// params[0] = inputLong;
				break;

			default:
				break;
			}

			Method theMethod = getMethod(methods);
			theMethod.getParameterTypes();
			if (theMethod != null) {
				Class[] types = theMethod.getParameterTypes();
				Class type = types[0];
				try {
					Object resultObj = null;
					switch (type.getName()) {
					case "int":
						resultObj = theMethod.invoke(scriptInstance, new Object[] { inputInt });
						break;

					case "String":
						resultObj = theMethod.invoke(scriptInstance, new Object[] { inputString });
						break;

					case "boolean":
						resultObj = theMethod.invoke(scriptInstance, new Object[] { inputBoolean });
						break;

					case "Long":
						resultObj = theMethod.invoke(scriptInstance, new Object[] { inputLong });
						break;

					default:
						resultObj = theMethod.invoke(scriptInstance, null);
						break;
					}
					result = resultObj.toString();
				} catch (Exception e) {
					LOGGER.error("Exception thrown in Method " + theMethod.getName());
					e.printStackTrace();
					result = e.getMessage();
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

	public static Method getMethod(Method[] methods) {
		for (int i = 0; i < methods.length; i++) {
			String methodReturnType = methods[i].getReturnType().getSimpleName();
			if (methodReturnType.equals("int") || methodReturnType.equals("String") || methodReturnType.equals("boolean") || methodReturnType.equals("Long")) {
				LOGGER.info("Returning found method: " + methods[i].getName());
				return methods[i];
			}

		}
		return null;
	}
}
