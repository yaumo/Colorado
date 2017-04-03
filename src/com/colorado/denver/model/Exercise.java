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

	private transient Set<Lecture> lectures;
	private Set<Solution> solutions;

	private Date deadline;
	private String videoLink;
	// template code
	private String code;
	private String solution_code;

	// The students who should do this exercise
	private transient Set<User> users;

	public Exercise() {

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
