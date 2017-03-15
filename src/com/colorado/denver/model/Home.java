package com.colorado.denver.model;

import javax.persistence.Entity;

@Entity
public class Home extends BaseEntity {
	public static final String HOME = "home";

	private String content;

	private String anotherContent;

	// no-argument constructor that is visible with at least protected scope is needed for hibernate
	public Home() {

	}

	public Home(String content) {
		this.content = content;
	}

	public Home(String content, String anotherContent) {
		this.content = content;
		this.anotherContent = anotherContent;
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
