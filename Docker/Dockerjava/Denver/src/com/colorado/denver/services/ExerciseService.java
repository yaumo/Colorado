package com.colorado.denver.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.LoggerFactory;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.model.Lecture;
import com.colorado.denver.model.Solution;
import com.colorado.denver.model.User;

public class ExerciseService {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExerciseService.class);

	@SuppressWarnings("unchecked")
	public static Set<Exercise> getAllExercisesForUser(String hibId) {

		@SuppressWarnings("rawtypes")
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

	public static Exercise getExerciseForSolution(Solution sol) {
		if (sol.getExercise() != null) {
			HibernateController hibCtrl = new HibernateController();
			Exercise exc = (Exercise) hibCtrl.getEntity(sol.getExercise().getId());
			return exc;

		}
		LOGGER.error("NO exercise for Solution found on entity! This shouldn't happen! Returning null...");
		return null;
	}

	public static boolean dueDateReached(Solution sol) {
		String deadLine = getExerciseForSolution(sol).getDeadline();
		Date today = Calendar.getInstance().getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date deadline = null;
		try {
			deadline = sdf.parse(deadLine);
		} catch (ParseException e) {
			LOGGER.error("Error parsing ");
			e.printStackTrace();
		}

		if (today.after(deadline)) {
			return true;
		} else {
			return false;
		}

	}

}
