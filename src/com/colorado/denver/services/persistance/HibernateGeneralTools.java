package com.colorado.denver.services.persistance;

import java.util.Objects;

import javax.management.ReflectionException;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.LoggerFactory;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.BaseEntity;
import com.colorado.denver.tools.GenericTools;

public class HibernateGeneralTools {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HibernateGeneralTools.class);

	private static final HibernateController hibCtrl = new HibernateController();

	public static HibernateController getHibernateController() {
		return hibCtrl;
	}

	public static <T extends BaseEntity> T getInitializedEntity(T entity) throws ReflectionException {
		return getInitializedEntity(entity, false);
	}

	public static <T extends BaseEntity> T getInitializedEntity(T entity, boolean reattachToSessionIfRequired) throws ReflectionException {
		Objects.requireNonNull(entity, "Entity expected!");
		Class<T> clazz = GenericTools.getClassForName(entity.getObjectClass());
		return getInitializedEntity(clazz, entity);
	}

	public static <T, U extends T> T getInitializedEntity(Class<U> clazz, BaseEntity<?> entity) {
		Objects.requireNonNull(entity, "Class expected!");
		Objects.requireNonNull(entity, "Entity expected!");
		Hibernate.initialize(entity);
		if (entity instanceof HibernateProxy) {
			entity = (BaseEntity) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
		} else {
			LOGGER.trace("{} is not a proxy, so don't initialize it.");
		}
		return clazz.cast(entity);
	}

}
