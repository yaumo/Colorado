package com.colorado.denver.services.security;

import com.colorado.denver.model.Exercise;
import com.colorado.denver.model.Lecture;
import com.colorado.denver.model.Solution;
import com.colorado.denver.model.User;
import com.colorado.denver.services.UserService;
import com.colorado.denver.tools.DenverConstants;

public class SecurityCheckRole {

	public static boolean checkRole(String className, String mode) {
		boolean allowed = false;

		if (mode.equals(DenverConstants.POST)) {
			switch (className) {
			case Solution.SOLUTION:
				allowed = true;
				break;
			case Lecture.LECTURE:
				if (UserService.isCurrentUserDocent()) {
					allowed = true;
				}
				break;
			case Exercise.EXERCISE:
				if (UserService.isCurrentUserDocent()) {
					allowed = true;
				}
				break;
			case User.USER:
				if (UserService.isCurrentUserDocent()) {
					allowed = true;
				}
				break;

			}
		} else if (mode.equals(DenverConstants.PATCH)) {
			switch (className) {
			case Solution.SOLUTION:
				allowed = true;
				break;
			case Lecture.LECTURE:
				if (UserService.isCurrentUserDocent()) {
					allowed = true;
				}
				break;
			case Exercise.EXERCISE:
				if (UserService.isCurrentUserDocent()) {
					allowed = true;
				}
				break;
			case User.USER:
				allowed = true;
				break;
			}
		} else if (mode.equals("GET")) {

		}

		return allowed;
	}

}
