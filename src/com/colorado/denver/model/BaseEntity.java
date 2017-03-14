package com.colorado.denver.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

//There should be NO instance of this entity! Create children of this entity instead!
public abstract class BaseEntity {
	public static final String ID = "id";
	public static final String OBJECT_CLASS = "objectClass";
	public static final String CREATOR = "creator";
	public static final String CREATION_DATE = "creationDate";
	public static final String HAS_BEEN_MODIFIED = "hasBeenModified";

	public String objectClass;
	public String creator; // TODO: use User Object provided by Security!
	public Date creationDate;
	public boolean hasBeenModified;

	@Id
	@GenericGenerator(name = "custom_id", strategy = "com.colorado.denver.services.persistance.HibernateIdGenerator")
	@GeneratedValue(generator = "custom_id")
	@Column(name = "id")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(String objectClass) {
		this.objectClass = objectClass;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isHasBeenModified() {
		return hasBeenModified;
	}

	public void setHasBeenModified(boolean hasBeenModified) {
		this.hasBeenModified = hasBeenModified;
	}

	public abstract String getPrefix();
}
