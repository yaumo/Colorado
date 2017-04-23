package com.colorado.denver.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class EducationEntity extends BaseEntity<EducationEntity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5282711422266704314L;
	public static final String EDUCATION_ENTITY = "educationEntity";
	public static final String OWNER = "owner";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String CREATION_DATE = "creationDate";

	@JsonIgnore
	private User owner;
	// We need this relation but not the actual JSON in the frontend.

	private String title;
	private String description;
	private String creationDate;

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

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
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
