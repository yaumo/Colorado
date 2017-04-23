package com.colorado.denver.services.codeExecution;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.el.MethodNotFoundException;

import org.slf4j.LoggerFactory;

import com.colorado.denver.tools.DenverConstants;

import groovy.lang.GroovyClassLoader;

public interface JavaExecutor {
	final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JavaExecutor.class);

	public default String executeJava(String[] excInput, String code, String entryMethod) throws SecurityException {
		String result = "";
		try {
			GroovyClassLoader gcl = new GroovyClassLoader();
			Class<?> scriptClass = gcl.parseClass(code);
			String className = scriptClass.getSimpleName();
			Object scriptInstance = scriptClass.newInstance();
			Method[] methods = scriptClass.getDeclaredMethods();

			Method theMethod = getMethod(methods, entryMethod);

			if (theMethod != null) {
				Class[] types = theMethod.getParameterTypes();
				Object[] inputObj = Converter.convertForJavaInput(types, excInput);

				try {
					Object resultObj = null;
					resultObj = theMethod.invoke(scriptInstance, inputObj);
					result = resultObj.toString();
				} catch (Exception e) {
					LOGGER.error("Exception thrown in Method " + theMethod.getName());
					e.printStackTrace();
					result = e.getMessage();
				} finally {
					gcl.close();
					// CLeanup
					File fileTemp = new File(className + ".class");
					if (fileTemp.exists()) {
						fileTemp.delete();
					}
				}

			} else {
				gcl.close();
				LOGGER.error("Method not found for entry Method: " + entryMethod);
				throw new MethodNotFoundException();
			}

		} catch (

		InstantiationException e) {
			e.printStackTrace();
			result = DenverConstants.EXC_THROWN + e.getClass().getCanonicalName();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			result = DenverConstants.EXC_THROWN + e.getClass().getCanonicalName();
		} catch (IOException e) {
			e.printStackTrace();
			result = DenverConstants.EXC_THROWN + e.getClass().getCanonicalName();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			result = DenverConstants.EXC_THROWN + e.getClass().getCanonicalName();
		} catch (MethodNotFoundException e) {
			e.printStackTrace();
			result = DenverConstants.EXC_THROWN + e.getClass().getCanonicalName() + " -- Are you sure the demanded method to implement is spelled correctly?";
		} catch (Exception e) {
			result = DenverConstants.EXC_THROWN + e.getMessage();
			e.printStackTrace();
		}
		System.out.println("Result is: " + result);
		return result;
	}

	public static Method getMethod(Method[] methods, String entryMethod) {
		for (int i = 0; i < methods.length; i++) {
			String methodName = methods[i].getName();
			if (methodName.equals(entryMethod)) {
				LOGGER.info("Returning found method: " + methods[i].getName());
				return methods[i];
			}

		}
		return null;
	}
}
