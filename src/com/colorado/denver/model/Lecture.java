package com.colorado.denver.model;

public class Lecture extends BaseEntity {
	//Vorlesung, Referenz auf den Kurs(Class) und eine Liste an Referenzen aller Klassen+ Dozenten
	public static final String LECTURE = "lecture";

	@Override
	public String getPrefix() {
		return LECTURE;
	}

}
