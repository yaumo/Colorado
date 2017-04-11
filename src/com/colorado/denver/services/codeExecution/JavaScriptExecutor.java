package com.colorado.denver.services.codeExecution;

import java.util.Iterator;
import java.util.Set;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.slf4j.LoggerFactory;

import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

public interface JavaScriptExecutor {
	final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JavaExecutor.class);

	@SuppressWarnings("restriction")
	public default String executeJavaScript(String inputType, String outputType, String excInput, String code) throws SecurityException {
		String result = "";
		try {
			NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
			ScriptEngine nashorn = factory.getScriptEngine(new NoJavaFilter());
			JSObject object = (JSObject) nashorn.eval(code);
			Invocable invocable = (Invocable) nashorn;
			Object results = invocable.invokeFunction("test", excInput);

			// call that anon function
			Set<String> keySet = object.keySet();
			int i = 0;
			for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
				String content = (String) iterator.next();
				System.out.println("Pos: " + i + "   Value: " + content);
				i++;
			}

			System.out.println("Object way: " + object.call(null, excInput));
			System.out.println("Invoke way: " + results);
			System.out.println("Invoke way class: " + results.getClass());
			result = results.toString();
		} catch (ScriptException e) {
			LOGGER.error("Error executing script itself");
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			LOGGER.error("No method");
			e.printStackTrace();
		}

		return result;

	}

	public class NoJavaFilter implements ClassFilter {

		@Override
		public boolean exposeToScripts(String s) {
			return false;
		}
	}

}
