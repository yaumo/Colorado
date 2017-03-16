package com.colorado.denver.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Exercise")
public class Exercise extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7599837012217952314L;
	public static final String EXERCISE = "exercise";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";

	public String title;
	public String description;

	public Exercise() {
	
	}
	
	
	public Exercise(String title, String description) {
		this.title = title;
		this.description = description;
		System.out.println(this.getClass().getName());
		System.out.println(this.getClass().getSimpleName());
		System.out.println(this.getClass().getCanonicalName());
		this.objectClass = this.getClass().getSimpleName();
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
