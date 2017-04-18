package com.colorado.denver.model;

import java.io.Serializable;
import java.util.Date;

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
	public static final String OBJECT_CLASS = "objectClass";
	public static final String CREATION_DATE = "creationDate";

	public transient Date creationDate;

	// Important for identifying the used object. This property is set during Hibernate create
	public String objectClass;
	private String hibId;// Because someone wants REST and self linking is only possible for this task with HATEOS

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

	public String getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(String objectClass) {
		this.objectClass = objectClass;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Transient
	public abstract String getPrefix();

	@Transient
	public abstract String setPrefix();

}
