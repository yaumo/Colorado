package com.colorado.denver.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -960714782698396108L;
	public static final String USER = "user";

	@Override
	public String getPrefix() {
		return USER;
	}

}
