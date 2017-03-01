package com.colorado.denver.services.javabytecoder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class Executor {
	private static final Class[] parameters = new Class[] { URL.class };

	public static void addFile(String s) throws IOException {
		File f = new File(s);
		addFile(f);
	}// end method

	public static void addFile(File f) throws IOException {
		System.out.println("Executing the stuff");
		execute(f.toURL());
	}// end method

	public static void execute(URL u) throws IOException {
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class sysclass = URLClassLoader.class;

		try {
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			System.out.println("Invoking....");
			method.invoke(sysloader, new Object[] { u });
		} catch (Throwable t) {
			t.printStackTrace();
			throw new IOException("Error, could not add URL to system classloader");
		}
	}

	public static void reinstantiateDummyClass() {
		String result = "It didn't work";
		// ByteBuddy myClass = new ByteBuddy().redefine(DummyClass.class);
		System.out.println(result);
	}

	Class<?> dynamicType = new ByteBuddy()
			.subclass(Executor.class)
			.method(ElementMatchers.named("toString"))
			.intercept(FixedValue.value("Hello World!"))
			.make()
			.load(getClass().getClassLoader(),
					ClassLoadingStrategy.Default.WRAPPER)
			.getLoaded();

}
