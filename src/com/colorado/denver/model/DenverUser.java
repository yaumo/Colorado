package com.colorado.denver.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DenverUser")
public class DenverUser extends BaseEntity {
	/**
	 * 
	 */

	private static final long serialVersionUID = -960714782698396108L;

	public static final String DENVER_USER = "DenverUser";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String SALT = "salt";
	public static final String ENABLED = "enabled";

	private String username;
	private String password;
	private String salt;
	protected boolean enabled;

	public void Denveruser() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String getPrefix() {
		return DENVER_USER;
	}

}
