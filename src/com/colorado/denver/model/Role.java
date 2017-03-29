package com.colorado.denver.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "role")
public class Role extends BaseEntity<Role> implements GrantedAuthority {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7881318559513459257L;

	public static final String ROLE = "role";
	public static final String ROLE_NAME = "roleName";

	// private String id;
	private String roleName;

	public Role() {

	}

	// @ManyToOne(cascade = CascadeType.ALL)
	// @JoinTable(name = "userrole", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	@Expose(serialize = false)
	private transient Set<User> users;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	// @ManyToOne(cascade = CascadeType.ALL)
	// @JoinTable(name = "userrole", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	// @Override
	// @Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	// public String getId() {
	// return id;
	// }
	//
	// @Override
	// public void setId(String id) {
	// this.id = id;
	// }

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	@Transient
	public String getPrefix() {

		return ROLE;
	}

	@Override
	@Transient
	public String setPrefix() {
		return getPrefix();
	}

	@Override
	@Transient
	public String getAuthority() {
		return this.getRoleName();
	}

}
