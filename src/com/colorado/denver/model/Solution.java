package com.colorado.denver.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Solution")
public class Solution extends EducationEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8150720003975451024L;
	// Klasse für die Lösung von Aufgaben, Refernz zu der Aufgabe und dem User
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	// ----------------- WRITE IN FUCKING ENGLISH MR. FRIDAY!!!!!
	// ..... (╯°□°)╯︵ ┻━┻

	public static final String SOLUTION = "solution";
	public static final String CODE = "code";
	public static final String EXERCISE = "exercise";

	private String code;
	// reference to the solved exercise
	private Exercise exercise;

	public Solution() {

	}

	@ManyToOne
	@JoinColumn(name = "exerciseID")
	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	@Transient
	public String getPrefix() {
		return SOLUTION;
	}

	@Override
	@Transient
	public String setPrefix() {
		return getPrefix();
	}

}
