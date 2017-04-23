package com.colorado.denver.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

//There should be NO instance of this entity! Create children of this entity instead!
@MappedSuperclass
public abstract class BaseEntity<T> implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2741548100143780881L;
	public static final String ID = "Id";

	private String id;

	public BaseEntity() {

	}

	@Id
	@GenericGenerator(name = "custom_id", strategy = "com.colorado.denver.services.persistence.HibernateIdGenerator")
	@GeneratedValue(generator = "custom_id")
	@Column(name = ID)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Transient
	@JsonIgnore
	public abstract String getPrefix();

	@Transient
	@JsonIgnore
	public abstract String setPrefix();

}
