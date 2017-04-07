package com.colorado.denver.view;

import com.colorado.denver.model.BaseEntity;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class GsonExclusionStrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		return (f.getDeclaringClass() == BaseEntity.class && f.getName().equals("crud"));
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}