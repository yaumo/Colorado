package com.colorado.denver.services.javabytecoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.LoggerFactory;

import com.colorado.denver.DenverApplication;

import groovy.lang.GroovyClassLoader;

public class JavaExecutor {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DenverApplication.class);

	public void executeJava() throws SecurityException {

		System.out.println("IN TEST!");
		try {

			InputStream is = new FileInputStream("fibonacci.txt");
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String line = buf.readLine();
			StringBuilder sb = new StringBuilder();

			while (line != null) {
				sb.append(line).append("\n");
				line = buf.readLine();
			}
			buf.close();
			String fileAsString = sb.toString();
			System.out.println(fileAsString);

			int n = 11;

			GroovyClassLoader gcl = new GroovyClassLoader();
			Class<?> scriptClass = gcl.parseClass(fileAsString);
			Object scriptInstance = scriptClass.newInstance();
			int result = (int) scriptClass.getDeclaredMethod("fibonacci", int.class).invoke(scriptInstance, new Object[] { n });
			System.out.println("Result is: " + result);
			gcl.close();

			// CLeanup
			File fileTemp = new File("Test.class");
			if (fileTemp.exists()) {
				fileTemp.delete();
			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
