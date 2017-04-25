package com.colorado.denver.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Exercise")
@DynamicUpdate(true)
@DynamicInsert(true)
@JsonIgnoreProperties({ "owner" })
public class Exercise extends EducationEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7599837012217952314L;
	public static final String EXERCISE = "exercise";
	public static final String CODE = "code";
	public static final String PATTERN_SOLUTION = "patternSolution";
	public static final String TEMPLATE = "template";
	public static final String VIDEO_LINK = "videoLink";
	public static final String ANSWER = "answer";
	public static final String HAS_BEEN_MODIFIED = "hasBeenModified";
	public static final String ENTRY_METHOD = "entryMethod";
	public static final String LANGUAGE = "language";
	public static final String MESSAGE = "message";

	private Set<Lecture> lectures;

	private String deadline;
	private String answer;

	@Transient
	private boolean hasBeenModified;

	@Transient
	private String message;

	private String videoLink;
	private String patternSolution;
	private String entryMethod;
	private String template;
	private String[] input;
	private String language;

	public Exercise() {

	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "lectures_exercises", joinColumns = { @JoinColumn(name = "exercises_id") }, inverseJoinColumns = { @JoinColumn(name = "lectures_id") })
	@Cascade({ CascadeType.ALL })
	@JsonBackReference
	public Set<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(Set<Lecture> lectures) {
		this.lectures = lectures;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	@Column(name = Exercise.VIDEO_LINK, columnDefinition = "TEXT")
	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	@Column(name = Exercise.PATTERN_SOLUTION, columnDefinition = "TEXT")
	public String getPatternSolution() {
		return patternSolution;
	}

	public void setPatternSolution(String solution_code) {
		this.patternSolution = solution_code;
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

	public String[] getInput() {
		return input;
	}

	public void setInput(String[] input) {
		this.input = input;
	}

	@Column(name = Exercise.ANSWER, columnDefinition = "TEXT")
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean isHasBeenModified() {
		return hasBeenModified;
	}

	public void setHasBeenModified(boolean hasBeenModified) {
		this.hasBeenModified = hasBeenModified;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(name = TEMPLATE, columnDefinition = "TEXT")
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getEntryMethod() {
		return entryMethod;
	}

	public void setEntryMethod(String entryMethod) {
		this.entryMethod = entryMethod;
	}

	@Column(name = Exercise.MESSAGE, columnDefinition = "TEXT")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
