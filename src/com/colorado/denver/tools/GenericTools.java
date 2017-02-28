package com.colorado.denver.tools;

import java.util.Set;

import org.reflections.Reflections;

import com.colorado.denver.DenverConstants;

public class GenericTools {
	private static final Reflections reflections = new Reflections(DenverConstants.COLORADO_PACKAGE);

	public static Reflections getReflections() {
		return reflections;
	}

	public static <T extends Object> Set<Class<? extends T>> getSubTypesOf(Class<T> clazz) {

		final Set<Class<? extends T>> classes = getReflections().getSubTypesOf(clazz);

		return classes;
	}

}
