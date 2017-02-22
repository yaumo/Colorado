package com.colorado.denver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class DenverApplication {

	public static void main(String[] args) {
		System.out.println("Running app!");
		// Use log4j
		SpringApplication.run(DenverApplication.class, args);
	}

}