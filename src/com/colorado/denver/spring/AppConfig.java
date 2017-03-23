package com.colorado.denver.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.colorado.denver.services.security.ActiveUserStore;

@Configuration
public class AppConfig {
	// beans

	@Bean
	public void userRepository() {

	}

	@Bean
	public ActiveUserStore activeUserStore() {
		return new ActiveUserStore();
	}

}