package com.colorado.denver.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import com.colorado.denver.controller.entityController.ExerciseController;
import com.colorado.denver.services.persistence.SessionTools;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FrontendControllerTests {

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@Before
	public void setUp() throws Exception {
		this.request = new MockHttpServletRequest();
		SessionTools.createSessionFactory(true);// TRUE due to UPDATE!!!
		request.setContentType("application/json");
		this.response = new MockHttpServletResponse();
	}

	@Test
	public void testExerciseController() {

		try {
			InputStream is = new FileInputStream("jsonTest.txt");
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String line = buf.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line).append("\n");
				line = buf.readLine();
			}
			String fileAsString = sb.toString();

			request = new MockHttpServletRequest("POST", "/exercise");
			request.setContentType("application/json");
			request.addHeader("Accept", "application/json, text/javascript, */*");
			request.setLocalPort(-1);
			request.setContent((fileAsString)
					.getBytes("UTF-8"));

			System.out.println(request.getRequestURI());
			System.out.println(request.getRequestURL());
			System.out.println(request.getServerName());
			ExerciseController mav = new ExerciseController();
			mav.handleExerciseRequest(request, response);

			assertEquals(200, response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
