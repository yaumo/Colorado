package com.colorado.denver.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "role")
public class Role extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7881318559513459257L;

	public static final String ROLE = "role";
	public static final String ROLE_NAME = "roleName";

	// private String id;
	private String roleName;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "userrole", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private User user;

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

	// @ManyToMany
	// public Set<User> getUsers() {
	// return users;
	// }
	//
	// public void setUsers(Set<User> users) {
	// this.users = users;
	// }

	@Override
	@Transient
	public String getPrefix() {

		return ROLE;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	@Transient
	public String setPrefix() {
		return getPrefix();
	}

}
