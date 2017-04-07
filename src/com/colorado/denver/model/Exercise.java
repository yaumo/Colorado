package com.colorado.denver.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "Exercise")
public class Exercise extends EducationEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7599837012217952314L;
	public static final String EXERCISE = "exercise";
	public static final String CODE = "code";
	public static final String SOLUTION_CODE = "solution_code";

	private transient Set<Lecture> lectures;
	// TODO: inversal JSON not possible! therefore we need transient. This is bad! Modify GSON?

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

	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade({ CascadeType.ALL })
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@OneToMany(fetch = FetchType.EAGER)
	@Cascade({ CascadeType.ALL })
	public Set<Solution> getSolutions() {
		return solutions;
	}

	public void setSolutions(Set<Solution> solutions) {
		this.solutions = solutions;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "lectures_exercises", joinColumns = { @JoinColumn(name = "lectures_id") }, inverseJoinColumns = { @JoinColumn(name = "exercises_id") })
	@Cascade({ CascadeType.ALL })
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

	@Column(name = Exercise.CODE, columnDefinition = "TEXT")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = Exercise.SOLUTION_CODE, columnDefinition = "TEXT")
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
