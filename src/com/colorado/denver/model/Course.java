package com.colorado.denver.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Course")
public class Course extends EducationEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 917641590246636493L;
	// Kurs
	public static final String COURSE = "course";

	public Course() {

	}

	@Override
	@Transient
	public String getPrefix() {
		return COURSE;
	}

	@Override
	public String setPrefix() {
		return getPrefix();
	}

}
