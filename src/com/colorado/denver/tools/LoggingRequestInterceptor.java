package com.colorado.denver.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.colorado.denver.DenverApplication;

import ch.qos.logback.classic.Level;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DenverApplication.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		Tools.setLoggingLevel(Level.DEBUG);
		traceRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		traceResponse(response);
		Tools.setLoggingLevel(Level.ERROR);
		return response;
	}

	private void traceRequest(HttpRequest request, byte[] body) throws IOException {
		LOGGER.debug("===========================request begin================================================");
		LOGGER.debug("URI         : {}", request.getURI());
		LOGGER.debug("Method      : {}", request.getMethod());
		LOGGER.debug("Headers     : {}", request.getHeaders());
		LOGGER.debug("Request body: {}", new String(body, "UTF-8"));
		LOGGER.debug("==========================request end================================================");
	}

	private void traceResponse(ClientHttpResponse response) throws IOException {
		StringBuilder inputStringBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
		String line = bufferedReader.readLine();
		while (line != null) {
			inputStringBuilder.append(line);
			inputStringBuilder.append('\n');
			line = bufferedReader.readLine();
		}
		LOGGER.debug("============================response begin==========================================");
		LOGGER.debug("Status code  : {}", response.getStatusCode());
		LOGGER.debug("Status text  : {}", response.getStatusText());
		LOGGER.debug("Headers      : {}", response.getHeaders());
		LOGGER.debug("Response body: {}", inputStringBuilder.toString());
		LOGGER.debug("=======================response end=================================================");
	}
}