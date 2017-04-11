package com.colorado.denver.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.colorado.denver.services.SecurityServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityServiceImpl customAuthenticationProvider;

	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.authenticationProvider(customAuthenticationProvider)
				.userDetailsService(auth.getDefaultUserDetailsService())
				.passwordEncoder(new BCryptPasswordEncoder());// This will probaply not work
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/").permitAll()
				.and()
				.formLogin().loginPage("/login")
				.defaultSuccessUrl("/exercise")
				.failureUrl("/login?error")
				.usernameParameter("username").passwordParameter("password")
				.and().csrf().disable()
				.logout().logoutSuccessUrl("/loginPage?logout");

	}

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}