package com.colorado.denver.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Home")
public class Home extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3587905596126634562L;

	public static final String HOME = "home";

	private String content;

	private String anotherContent;

	// no-argument constructor that is visible with at least protected scope is needed for hibernate
	public Home() {
	}

	public Home(String content, String anotherContent) {
		this.content = content;
		this.anotherContent = anotherContent;
		this.objectClass = this.getClass().getSimpleName();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAnotherContent() {
		return anotherContent;
	}

	public void setAnotherContent(String anotherContent) {
		this.anotherContent = anotherContent;
	}

	@Override
	public String getPrefix() {

		return HOME;
	}
}
