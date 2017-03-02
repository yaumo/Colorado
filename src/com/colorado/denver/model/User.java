package com.colorado.denver.model;

public class User extends BaseEntity {
	//User-Klasse, Referenz auf den Kurs(hier Class)
	public static final String USER = "user";
	@Override
	public String getPrefix() {
		return USER;
	}

}
