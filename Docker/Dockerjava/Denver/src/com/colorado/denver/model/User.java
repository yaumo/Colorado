package com.colorado.denver.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "UserDenver")
public class User extends BaseEntity<User> {
	/**
	 * 
	 */

	private static final long serialVersionUID = -960714782698396108L;

	public static final String DENVER_USER = "DenverUser";
	public static final String USER = "user";
	public static final String USERS = "users";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String SALT = "salt";
	public static final String ENABLED = "enabled";
	public static final String PRIVILEGES = "privileges";

	private String username;
	private Set<Lecture> lectures;

	@JsonProperty(access = Access.WRITE_ONLY)
	private transient String password;

	@JsonProperty(access = Access.WRITE_ONLY)
	protected transient boolean enabled;

	private Course course;
	private Set<Solution> solutions;
	private Set<Privilege> privileges;

	public User() {
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "lectures_users", joinColumns = { @JoinColumn(name = "lectures_id") }, inverseJoinColumns = { @JoinColumn(name = "users_id") })
	@JsonManagedReference
	public Set<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(Set<Lecture> lectures) {
		this.lectures = lectures;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public Set<Solution> getSolutions() {
		return solutions;
	}

	public void setSolutions(Set<Solution> solutions) {
		this.solutions = solutions;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "courseID")
	@JsonManagedReference
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_privileges", joinColumns = { @JoinColumn(name = "privileges_id") }, inverseJoinColumns = { @JoinColumn(name = "users_id") })
	@JsonManagedReference
	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> Privileges) {
		this.privileges = Privileges;
	}

	// public Collection<GrantedAuthority> getAuthorities() {
	// // make everyone Privilege_GLOBAL_ADMINISTRATOR
	// Collection<GrantedAuthority> grantedAuthorities = new ArraySet<GrantedAuthority>();
	// GrantedAuthority grantedAuthority = new GrantedAuthority() {
	// // anonymous inner type
	// @Override
	// public String getAuthority() {
	// return UserService.Privilege_GLOBAL_ADMINISTRATOR;
	// }
	// };
	// grantedAuthorities.add(grantedAuthority);
	// return grantedAuthorities;
	// }

	// public Collection<GrantedAuthority> getAllAuthorities(BaseEntity<?>
	// scope) {
	// if (scope != null) {
	// scope = HibernateGeneralTools.getInitializedEntity(scope);
	// }
	// return GenericTools.getSecurityService().getAllAuthorities(this, scope);
	// }
	//
	// public UserDetails getDetails(){
	// if(details == null){
	// details = GenericTools.ge
	// }
	// return details;
	// }

	@Override
	@Transient
	public String getPrefix() {
		return USER;
	}

	@Override
	@Transient
	public String setPrefix() {
		return getPrefix();
	}

}
