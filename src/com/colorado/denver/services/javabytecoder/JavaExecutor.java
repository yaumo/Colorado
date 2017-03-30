package com.colorado.denver.services.javabytecoder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import groovy.lang.GroovyClassLoader;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

public class JavaExecutor {
	public void executeJava() throws InstantiationException, IllegalAccessException, IOException, ResourceException, ScriptException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		System.out.println("IN TEST!");
		InputStream is = new FileInputStream("fibonacci.txt");
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));
		String line = buf.readLine();
		StringBuilder sb = new StringBuilder();

		while (line != null) {
			sb.append(line).append("\n");
			line = buf.readLine();
		}
		String fileAsString = sb.toString();
		System.out.println(fileAsString);

		int n = 11;

		GroovyClassLoader gcl = new GroovyClassLoader();
		Class<?> scriptClass = gcl.parseClass(fileAsString);
		Object scriptInstance = scriptClass.newInstance();

		int result = (int) scriptClass.getDeclaredMethod("fibonacci", int.class).invoke(scriptInstance, new Object[] { n });
		System.out.println(result);

		// http://bytebuddy.net/#/tutorial See 'target'
	}
}
