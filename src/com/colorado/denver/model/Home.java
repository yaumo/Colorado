package com.colorado.denver.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
 
@Entity
public class Home extends BaseEntity {
	public static final String HOME = "home";

	@Id
	@GenericGenerator(name = "custom_id", strategy = "com.colorado.denver.idGenerators.HomeIdGenerator")
	@GeneratedValue(generator = "custom_id")
	@Column(name = "id")
	private String id;
	
	private String content;

	public Home(){
		
	}
	
	public Home(String content) {
		this.content = content;
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
}
