package com.colorado.denver.idGenerators;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;


public class HomeIdGenerator implements IdentifierGenerator {

    private String sequenceName = "home_sequence";
    
    @Override
    public Serializable generate(SessionImplementor sessionImpl, Object data)
            throws HibernateException {
        Serializable result = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String prefix = "Hom";
            connection = sessionImpl.connection();
            statement = connection.createStatement();                   
             try {  
            	 statement.execute("DO\n$$\nBEGIN\nCREATE SEQUENCE public.home_sequence minvalue 0 start with 0;\nEXCEPTION WHEN duplicate_table THEN\nEND\n$$ LANGUAGE plpgsql;");
                 resultSet = statement.executeQuery("SELECT  NEXTVAL('"+sequenceName+"')");
             } catch(Exception ex) {
                 ex.printStackTrace();
             }
            
            if(resultSet.next()) {
                int nextValue = resultSet.getInt(1);                
                String suffix = String.format("%05d", nextValue + 1);               
                result = prefix.concat(suffix);
                System.out.println("Custom generated Sequence value : "+result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
