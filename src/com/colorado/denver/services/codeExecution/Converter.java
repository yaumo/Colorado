package com.colorado.denver.services.codeExecution;

public class Converter {

	public static Object[] convertForJavaScriptInput(String[] inputs) throws Exception {

		Object[] obj = new Object[inputs.length];
		for (int i = 0; i < obj.length; i++) {
			if (inputs[i].matches("[0-9]+")) {
				obj[i] = convertInt(inputs[i]);
			} else if (inputs[i].equals("true") || inputs[i].equals("false")) {
				obj[i] = convertBol(inputs[i]);
			} else {
				obj[i] = inputs[i];
			}
		}
		return obj;
	}

	public static Object[] convertForJavaInput(@SuppressWarnings("rawtypes") Class[] types, String[] inputs) throws Exception {

		if (types.length != inputs.length) {
			throw new Exception("Your parameters are not matching the method declaration!");
		}

		for (int i = 0; i < inputs.length; i++) {
			System.out.println("Pos: " + i + " type: " + types[i].getName() + " || input: " + inputs[i]);
		}

		Object[] obj = new Object[inputs.length];
		for (int i = 0; i < obj.length; i++) {

			switch (types[i].getName()) {
			case "java.lang.String":
				obj[i] = inputs[i];
				break;

			case "int":
				obj[i] = convertInt(inputs[i]);
				break;

			case "java.lang.Long":
				obj[i] = convertLong(inputs[i]);
				break;

			case "boolean":
				obj[i] = convertBol(inputs[i]);
				break;

			default:
				System.out.println("Error at line: " + i);
				return null;
			}
		}

		return obj;
	}

	public static Boolean convertBol(String arr) {

		if (arr.equals("true")) {
			return true;
		} else {
			return false;
		}

	}

	public static Long convertLong(String arr) {

		try {
			return Long.parseLong(arr);
		} catch (Exception nfe) {
			nfe.printStackTrace();
			System.out.println("DAMN LONG");
			return null;
		}

	}

	public static Integer convertInt(String arr) {
		try {
			return Integer.parseInt(arr);
		} catch (Exception nfe) {
			nfe.printStackTrace();
			System.out.println("DAMN INT");
			return null;
		}

	}
}
