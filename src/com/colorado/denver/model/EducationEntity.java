package com.colorado.denver.model;

import javax.persistence.Transient;

public class EducationEntity extends BaseEntity<EducationEntity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5282711422266704314L;
	public static final String EDUCATION_ENTITY = "educationEntity";
	public static final String OWNER = "owner";

	private User owner;

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
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
