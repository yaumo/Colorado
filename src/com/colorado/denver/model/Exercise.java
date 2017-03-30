package com.colorado.denver.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Exercise")
public class Exercise extends EducationEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7599837012217952314L;
	public static final String EXERCISE = "exercise";
	public static final String EXERCISE_TITLE = "title";
	public static final String EXERCISE_DESCRIPTION = "description";

	private transient Set<Lecture> lectures;
	private Set<Solution> solutions;

	private String title;
	private String description;
	private Date deadline;
	private String videoLink;
	// template code
	private String code;
	private String solution_code;
	private transient Set<User> users;

	public Exercise() {

	}

	public Exercise(String title, String description) {
		this.title = title;
		this.description = description;
		System.out.println(this.getClass().getName());
		System.out.println(this.getClass().getSimpleName());
		System.out.println(this.getClass().getCanonicalName());
		this.objectClass = this.getClass().getSimpleName();
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<Solution> getSolutions() {
		return solutions;
	}

	public void setSolutions(Set<Solution> solutions) {
		this.solutions = solutions;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(Set<Lecture> lectures) {
		this.lectures = lectures;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSolution_code() {
		return solution_code;
	}

	public void setSolution_code(String solution_code) {
		this.solution_code = solution_code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	@Transient
	public String getPrefix() {
		return EXERCISE;
	}

	@Override
	@Transient
	public String setPrefix() {
		return getPrefix();
	}

}
