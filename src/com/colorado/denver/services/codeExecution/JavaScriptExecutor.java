
package com.colorado.denver.services.codeExecution;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.slf4j.LoggerFactory;

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

			result = object.call(null, excInput).toString();
			LOGGER.debug("Result of JavaScript Calc is: " + result);
		} catch (ScriptException e) {
			LOGGER.error("Error executing script itself");
			e.printStackTrace();
			result = e.toString();
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
