package com.colorado.denver.model;

import javax.persistence.Transient;

public class EducationEntity extends BaseEntity<EducationEntity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5282711422266704314L;
	public static final String EDUCATION_ENTITY = "educationEntity";
	public static final String OWNER = "owner";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";

	private User owner;
	private String title;
	private String description;

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
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
	@Transient
	public String getPrefix() {
		return EDUCATION_ENTITY;
	}

	@Override
	@Transient
	public String setPrefix() {
		return getPrefix();
	}

}
