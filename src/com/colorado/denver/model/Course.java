package com.colorado.denver.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Course")
public class Course extends EducationEntity {
	/**
	 * 
	 */
	public static final String COURSE = "course";
	public static final String COURSE_TITLE = "course_title";
	public static final String COURSE_STUDENTS = "course_STUDENTS";
	public static final String COURSE_LECTURES = "course_LECTURES";

	private static final long serialVersionUID = 917641590246636493L;

	private String course_title;
	private transient Set<User> users;
	private Set<Lecture> lectures;

	public Course() {

	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(Set<Lecture> lectures) {
		this.lectures = lectures;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<User> getUsers() {
		return users;
	}

	public void setStudents(Set<User> students) {
		this.users = students;
	}

	public String getCourse_title() {
		return course_title;
	}

	public String setCourse_title(String course_title) {
		this.course_title = course_title;
		return this.course_title;
	}

	@Override
	@Transient
	public String getPrefix() {
		return COURSE;
	}

	@Override
	public String setPrefix() {
		return getPrefix();
	}

}
