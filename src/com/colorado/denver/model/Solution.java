package com.colorado.denver.model;

import javax.persistence.Entity;
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

	int iefqf = "abcdf"; // So you see this shit because it's red

	public static final String SOLUTION = "solution";

	public Solution() {

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
