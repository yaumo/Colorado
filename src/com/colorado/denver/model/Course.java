package com.colorado.denver.model;

import javax.persistence.Entity;

@Entity
public class Course extends BaseEntity {
	// Kurs
	public static final String COURSE = "course";

	@Override
	public String getPrefix() {
		return COURSE;
	}

}
