package com.colorado.denver.model;

import javax.persistence.Entity;

@Entity
public class Exercise extends BaseEntity {
	public static final String EXERCISE = "exercise";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";

	public String title;
	public String description;

	public Exercise() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getPrefix() {
		return EXERCISE;
	}

}
