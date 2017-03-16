package com.colorado.denver.model;

import javax.persistence.Entity;

@Entity
public class Lecture extends BaseEntity {
	public static final String LECTURE = "lecture";

	@Override
	public String getPrefix() {
		return LECTURE;
	}

}
