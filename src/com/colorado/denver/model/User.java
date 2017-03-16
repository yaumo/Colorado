package com.colorado.denver.model;

import javax.persistence.Entity;

@Entity
public class User extends BaseEntity {
	public static final String USER = "user";

	@Override
	public String getPrefix() {
		return USER;
	}

}
