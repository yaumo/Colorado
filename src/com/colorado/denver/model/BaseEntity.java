package com.colorado.denver.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.ResourceSupport;

//There should be NO instance of this entity! Create children of this entity instead!
@MappedSuperclass
public abstract class BaseEntity<T> extends ResourceSupport implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2741548100143780881L;
	public static final String HIB_ID = "hibId";

	private String hibId;

	public BaseEntity() {

	}

	@Id
	@GenericGenerator(name = "custom_hibId", strategy = "com.colorado.denver.services.persistence.HibernateIdGenerator")
	@GeneratedValue(generator = "custom_hibId")
	@Column(name = "hibId")
	public String getHibId() {
		return hibId;
	}

	public void setHibId(String hibId) {
		this.hibId = hibId;
	}

	@Transient
	public abstract String getPrefix();

	@Transient
	public abstract String setPrefix();

}
