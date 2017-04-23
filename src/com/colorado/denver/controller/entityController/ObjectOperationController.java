package com.colorado.denver.controller.entityController;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import com.colorado.denver.controller.HibernateController;
import com.colorado.denver.model.BaseEntity;
import com.colorado.denver.model.EducationEntity;
import com.colorado.denver.model.User;
import com.colorado.denver.services.persistence.HibernateGeneralTools;
import com.colorado.denver.services.security.SecurityCheckPrivilege;
import com.colorado.denver.services.user.UserService;
import com.colorado.denver.tools.DenverConstants;

public class ObjectOperationController {
	/**
	 * 
	 */
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ObjectOperationController.class);
	private HibernateController hibCtrl = HibernateGeneralTools.getHibernateController();

	private User workingUser = null;

	public boolean checkAccess(String className, String mode) {
		boolean allowed = false;
		allowed = SecurityCheckPrivilege.checkPrivilege(className, mode);
		this.workingUser = UserService.getCurrentUser();
		return allowed;
	}

	public BaseEntity<?> doDatabaseOperation(BaseEntity<?> entity, String mode) {

		String id = DenverConstants.ERROR;
		try {
			id = entity.getId();

		} catch (Exception e) {
			LOGGER.error("Error extraxting information from the Object(FromJSON): " + entity.getId());
			e.printStackTrace();
		}
		switch (mode) {
		case DenverConstants.POST:
			id = create(entity);
			entity.setId(id);
			break;
		case DenverConstants.PATCH:
			update(entity);
			break;
		case DenverConstants.DELETE:
			delete(id);
			break;

		default:
			LOGGER.error("ERROR IN ASSERTING A OPERTAION!! VALUE: " + mode);
			throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);// last resort
		}
		workingUser = null;
		LOGGER.debug("Returning Entity in " + mode + "mode: " + id);
		return read(id);
	}

	private String create(BaseEntity<?> entity) {
		if (entity instanceof EducationEntity) {
			((EducationEntity) entity).setOwner(workingUser);
		}

		String id = hibCtrl.addEntity(entity);
		return id;
	}

	private BaseEntity<?> read(String id) {
		return hibCtrl.getEntity(id);
	}

	private BaseEntity<?> update(BaseEntity<?> entity) {
		if (entity instanceof EducationEntity) {
			((EducationEntity) entity).setOwner(workingUser);
		}

		try {
			return hibCtrl.mergeEntity(entity);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("MERGE failed! Trying update");

			return hibCtrl.updateEntity(entity, entity.getId());
		}

	}

	private boolean delete(String id) {
		try {
			hibCtrl.deleteEntity(id);
			return true;
		} catch (Exception e) {
			LOGGER.error("Something went wrong while deleting: " + id);
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("null")
	public static List<String> extractAllRequestPostParameters(HttpServletRequest req) throws IOException {
		Enumeration<String> parameterNames = req.getParameterNames();
		List<String> paramValues = null;

		while (parameterNames.hasMoreElements()) {
			paramValues.add(req.getParameter(parameterNames.nextElement()));
		}
		return paramValues;
	}
}
