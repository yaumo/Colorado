package com.colorado.denver.services.persistance;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.colorado.denver.DenverConstants;
import com.colorado.denver.model.BaseEntity;

public class HibernateIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor sessionImpl, Object obj)
			throws HibernateException {
		Serializable result = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			BaseEntity clazz = (BaseEntity) obj;
			String prefix = clazz.getPrefix();
			String sequence = prefix + DenverConstants.UNDERSCORE + "sequence";
			connection = sessionImpl.connection();
			statement = connection.createStatement();
			statement.execute(
					"DO\n$$\nBEGIN\nCREATE SEQUENCE public.home_sequence minvalue 0 start with 0;\nEXCEPTION WHEN duplicate_table THEN\nEND\n$$ LANGUAGE plpgsql;");
			resultSet = statement.executeQuery("SELECT  NEXTVAL('" + sequence + "')");

			if (resultSet.next()) {
				int nextValue = resultSet.getInt(1);
				String suffix = String.format("%05d", nextValue + 1);
				result = prefix.concat(suffix);
				System.out.println("Custom generated Sequence value : " + result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
