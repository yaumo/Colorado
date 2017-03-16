package com.colorado.denver.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Course")
public class Course extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 917641590246636493L;
	// Kurs
	public static final String COURSE = "course";

	@Override
	public String getPrefix() {
		return COURSE;
	}

}
