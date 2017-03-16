package com.colorado.denver.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Lecture")
public class Lecture extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3837760136840854153L;
	public static final String LECTURE = "lecture";

	@Override
	public String getPrefix() {
		return LECTURE;
	}

}
