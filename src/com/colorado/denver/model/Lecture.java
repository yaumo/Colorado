package com.colorado.denver.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Lecture")
public class Lecture extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3837760136840854153L;
	public static final String LECTURE = "lecture";

	public Lecture(){
		
	}
	
	@Override
	@Transient
	public String getPrefix() {
		return LECTURE;
	}

	@Override
	@Transient
	public String setPrefix() {
		return getPrefix();
	}

}
