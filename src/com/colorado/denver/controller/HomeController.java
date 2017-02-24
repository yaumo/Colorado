package com.colorado.denver.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.colorado.denver.DenverConstants;
import com.colorado.denver.model.Home;

@RestController
public class HomeController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	public class Target {
		public String hello(String name) {
			return "Hello " + name + "!";
		}
	}

	public static final String sampleCode = "execute(int n) {for (int i = 0; i < n; i++) {n++;}System.out.println(n);}";

	@RequestMapping(DenverConstants.FORWARD_SLASH + Home.HOME)
	public Home homeMessage(@RequestParam(value = "name", defaultValue = "World") String name) {
		// execute unknown class

		return new Home(counter.incrementAndGet() + "", String.format(template, name));
	}

	public static void execute(int n) {
		for (int i = 0; i < n; i++) {
			n++;
		}
		System.out.println("The Result is: " + n);
	}
}
