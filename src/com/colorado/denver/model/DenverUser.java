package com.colorado.denver.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DenverUser")
public class DenverUser extends BaseEntity {
	/**
	 * 
	 */
	
	public void Denveruser(){
		
	}
	
	private static final long serialVersionUID = -960714782698396108L;
	public static final String DENVER_USER = "DenverUser";

	@Override
	public String getPrefix() {
		return DENVER_USER;
	}

}
