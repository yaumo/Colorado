package com.colorado.denver.services.codeExecution;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.slf4j.LoggerFactory;

import com.colorado.denver.tools.DenverConstants;

import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

@SuppressWarnings("restriction")
public interface JavaScriptExecutor {
	final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JavaExecutor.class);

	public default String executeJavaScript(String[] excInput, String code) {
		String result = "";
		try {
			LOGGER.info("Starting...JS execution");
			NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
			ScriptEngine nashorn = factory.getScriptEngine(new NoJavaFilter());
			JSObject object = (JSObject) nashorn.eval(code);

			Object[] inputObj = Converter.convertForJavaScriptInput(excInput);
			result = object.call(null, inputObj).toString();
			LOGGER.info("Finished...");
			System.out.println("Result of JavaScript Calc is: " + result);
		} catch (ScriptException e) {
			LOGGER.error("Error executing script itself" + result);

			e.printStackTrace();
			result = DenverConstants.EXC_THROWN + e.toString();
		} catch (Exception e) {
			LOGGER.error("generic error in JS executor!");

			e.printStackTrace();
			result = DenverConstants.EXC_THROWN + DenverConstants.U_ERROR + e.getMessage();
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
