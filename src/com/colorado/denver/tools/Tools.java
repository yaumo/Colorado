package com.colorado.denver.tools;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

public class Tools {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Tools.class);

	public static void printJsonObject(JSONObject jObject) throws JSONException {
		Iterator<?> keys = jObject.keys();
		LOGGER.debug("=======PRINTING JSON========");
		while (keys.hasNext()) {
			String key = (String) keys.next();
			if (jObject.get(key) instanceof JSONObject) {
				LOGGER.debug(key);
			}
		}
		LOGGER.debug("=======END OF JSON========");
	}

	public static void printGson(String json) {
		LOGGER.debug("=======PRINTING JSON========");
		System.out.println(json);
		LOGGER.debug("=======END OF JSON========");
	}
}
