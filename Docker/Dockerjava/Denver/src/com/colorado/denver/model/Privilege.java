package com.colorado.denver.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "privilege")
public class Privilege extends BaseEntity<Privilege> implements GrantedAuthority {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7881318559513459257L;

	public static final String PRIVILEGE = "privilege";
	public static final String PRIVILEGE_NAME = "privilegeName";

	private String privilegeName;

	public Privilege() {

	}

	private transient Set<User> users;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_privileges", joinColumns = { @JoinColumn(name = "users_id") }, inverseJoinColumns = { @JoinColumn(name = "privileges_id") })
	@JsonBackReference
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> systemUsers) {
		this.users = systemUsers;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String PrivilegeName) {
		this.privilegeName = PrivilegeName;
	}

	@Override
	@Transient
	public String getPrefix() {

		return PRIVILEGE;
	}

	@Override
	@Transient
	public String setPrefix() {
		return getPrefix();
	}

	@Override
	@Transient
	public String getAuthority() {
		return this.getPrivilegeName();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Privilege [id=").append(this.getId()).append(", name=").append(this.getPrivilegeName()).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.getId() == null) ? 0 : this.getId().hashCode());
		result = (prime * result) + ((this.getPrivilegeName() == null) ? 0 : this.getPrivilegeName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Privilege other = (Privilege) obj;
		if (this.getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!this.getId().equals(other.getId())) {
			return false;
		}
		if (this.getPrivilegeName() == null) {
			if (other.getPrivilegeName() != null) {
				return false;
			}
		} else if (!this.getPrivilegeName().equals(other.getPrivilegeName())) {
			return false;
		}
		return true;
	}

}
