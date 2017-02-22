package com.colorado.denver.model;

public class Home {
	public static final String HOME = "home";

	private final long id;
	private final String content;

	public Home(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}
