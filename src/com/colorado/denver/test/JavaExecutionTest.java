package com.colorado.denver.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.colorado.denver.services.javabytecoder.JavaExecutor;

import groovy.util.ResourceException;
import groovy.util.ScriptException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaExecutionTest {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JavaExecutionTest.class);

	@Test
	public void executeJava() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, IOException, ResourceException, ScriptException {
		LOGGER.info("Start of bytebuddy");

		JavaExecutor dC = new JavaExecutor();
		dC.executeJava();

		LOGGER.info("End of bytebuddy");
	}
}
