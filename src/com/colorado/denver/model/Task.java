package com.colorado.denver.model;

public class Task extends BaseEntity {
	public static final String TASK = "task";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";

	public String title;
	public String description;

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

}
