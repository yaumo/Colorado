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
	private static String course_title;
	private static final long serialVersionUID = 917641590246636493L;
	public static final String COURSE = "course";

	private transient Set<User> students;
	private Set<Lecture> lectures;

	public Course() {

	}

	public Course(String course_title) {
		this.course_title = course_title;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(Set<Lecture> lectures) {
		this.lectures = lectures;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<User> getStudents() {
		return students;
	}

	public void setStudents(Set<User> students) {
		this.students = students;
	}

	public static String getCourse_title() {
		return course_title;
	}

	public static void setCourse_title(String course_title) {
		Course.course_title = course_title;
	}

}
