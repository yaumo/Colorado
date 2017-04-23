package com.colorado.denver.services;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.LoggerFactory;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.model.Lecture;
import com.colorado.denver.model.User;

public class ExerciseService {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExerciseService.class);

	public static Set<Exercise> getAllExercisesForUser(String hibId) {
		Set<Exercise> exercises = new HashSet();
		try {
			HibernateController hibCtrl = new HibernateController();

			User usr = (User) hibCtrl.getEntity(hibId);
			Set<Lecture> lectures = usr.getLectures();
			if (!lectures.isEmpty()) {
				for (Iterator iterator = lectures.iterator(); iterator.hasNext();) {
					Lecture lecture = (Lecture) iterator.next();
					Set<Exercise> exercisesInLectures = lecture.getExercises();
					for (Iterator iterator2 = exercisesInLectures.iterator(); iterator2.hasNext();) {
						Exercise exercise = (Exercise) iterator2.next();
						exercises.add(exercise);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error in ExerciseService while getting Exercises on User: " + hibId);
		}

		return exercises;
	}

}
