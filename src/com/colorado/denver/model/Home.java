package com.colorado.denver.model;

public class Home extends BaseEntity {
	public static final String HOME = "home";

	private final String content;

	public Home(String id, String content) {
		this.id = id;
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
