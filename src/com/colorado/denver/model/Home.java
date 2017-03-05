package com.colorado.denver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

@Entity
public class Home extends BaseEntity {
	public static final String HOME = "home";
	
	@Id
	@GenericGenerator(name = "custom_id", strategy = "com.colorado.denver.services.persistance.HibernateIdGenerator")
	@GeneratedValue(generator = "custom_id")
	@Column(name = "id")
	private String id;

	private String content;
	
	private String anotherContent;

	//no-argument constructor that is visible with at least protected scope is needed for hibernate
	public Home() {

	}

	public Home(String content) {
		this.content = content;
	}
	
	public Home(String content, String anotherContent) {
		this.content = content;
		this.anotherContent = anotherContent;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
