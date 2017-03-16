package com.colorado.denver.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Solution")
public class Solution extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8150720003975451024L;
	// Klasse für die Lösung von Aufgaben, Refernz zu der Aufgabe und dem User
	public static final String SOLUTION = "solution";

	@Override
	public String getPrefix() {
		return SOLUTION;
	}

}
