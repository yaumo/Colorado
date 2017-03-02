package com.colorado.denver.model;

public class Solution extends BaseEntity {
	//Klasse für die Lösung von Aufgaben, Refernz zu der Aufgabe und dem User
	public static final String SOLUTION = "solution";
	@Override
	public String getPrefix() {
		return SOLUTION;
	}

}
