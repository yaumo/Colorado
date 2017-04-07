package com.colorado.denver.model;

import java.util.Set;

import javax.persistence.CascadeType;
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
	public static final String LECTURE_USERS = "lecture_users";
	public static final String LECTURE_COURSE = "lecture_course";

	private Course course;
	private Set<Exercise> exercises;

	public Lecture() {

	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	public Set<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(Set<Exercise> exercises) {
		this.exercises = exercises;
	}

	@ManyToOne(cascade = { CascadeType.ALL })
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
