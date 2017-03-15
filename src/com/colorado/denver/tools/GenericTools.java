package com.colorado.denver.tools;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.management.ReflectionException;

import org.apache.commons.logging.impl.Log4JLogger;
import org.reflections.Reflections;

import com.colorado.denver.model.BaseEntity;

public class GenericTools {
	private static final Reflections reflections = new Reflections(DenverConstants.COLORADO_PACKAGE);
	private static final Log4JLogger LOGGER = new Log4JLogger();
	private Class<? extends BaseEntity> objectClass;

	public static Reflections getReflections() {
		return reflections;
	}

	public static <T extends Object> Set<Class<? extends T>> getSubTypesOf(Class<T> clazz) {

		final Set<Class<? extends T>> classes = getReflections().getSubTypesOf(clazz);

		return classes;
	}

	@SuppressWarnings("unchecked") // cast is safe as only databaseentities are allowed
	public <T extends BaseEntity> Class<T> getObjectClass() {
		return (Class<T>) objectClass;
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClassForName(String className) throws ReflectionException {
		try {
			return (Class<T>) Class.forName(className);
		} catch (final ClassNotFoundException e) {
			throw new ReflectionException(
					e);
		}
	}

	public static <T> T newInstanceOf(String clazz) throws ReflectionException {
		return newInstanceOf(getClassForName(clazz));
	}

	public static <T> T newInstanceOf(Class<T> clazz) throws ReflectionException {
		return newInstanceOf(clazz, false);
	}

	public static <T> Constructor[] getConstructorForClass(Class<T> clazz) {
		return clazz.getDeclaredConstructors();
	}

	public static <T> T newInstanceOf(Class<T> clazz, boolean allowNonPublicConstructors) throws ReflectionException {
		try {
			LOGGER.debug("Trying to initialize instance of {" + clazz + "} - allow non public constructors? {" + allowNonPublicConstructors + "}");
			try (Stream<Constructor<?>> stream = Arrays.stream(clazz.getDeclaredConstructors())) {

				final Optional<Constructor<?>> constructor = stream
						.filter(c -> c.getParameterCount() == 0) // find constructor with no parameters, no matter if public or non-public
						.findFirst();

				final Constructor<?> c = constructor.orElse(null);
				if (allowNonPublicConstructors && !c.isAccessible()) {
					c.setAccessible(true);
				} else {

				}

				return (T) c.newInstance();

			}

		} catch (ReflectiveOperationException e) {
			throw new ReflectionException(e, "Unable to instantiate " + clazz);
		}
	}

}
