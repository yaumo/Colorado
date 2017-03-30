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
	private static String title;
	private transient Set<User> users;
	private Course course;
	private transient Set<Exercise> exercises;

	public Lecture(String title) {
		this.title = title;
	}

	public Lecture() {

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

	public static String getTitle() {
		return title;
	}

	public static void setTitle(String lecture_title) {
		Lecture.title = lecture_title;
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
