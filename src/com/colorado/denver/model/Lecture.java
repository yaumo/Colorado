package com.colorado.denver.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Lecture")
public class Lecture extends EducationEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3837760136840854153L;

	public static final String LECTURE = "lecture";
	public static final String LECTURE_TITLE = "lecture_title";
	public static final String LECTURE_USERS = "lecture_users";
	public static final String LECTURE_COURSE = "lecture_course";

	private String title;
	private transient Set<User> users;
	private Course course;
	private transient Set<Exercise> exercises;

	public Lecture() {

	}

	public String getTitle() {
		return title;
	}

	public String setTitle(String lecture_title) {
		this.title = lecture_title;
		return this.title;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	public Set<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(Set<Exercise> exercises) {
		this.exercises = exercises;
	}

	@ManyToOne
	@JoinColumn(name = "courseID")
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
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
