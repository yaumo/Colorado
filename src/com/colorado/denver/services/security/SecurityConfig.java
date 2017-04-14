package com.colorado.denver.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.colorado.denver.services.SecurityServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityServiceImpl customAuthenticationProvider;

	private SecurityFilter customLoginFilter = new SecurityFilter();

	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.authenticationProvider(customAuthenticationProvider)
				.userDetailsService(auth.getDefaultUserDetailsService())
				.passwordEncoder(new BCryptPasswordEncoder());// This will probaply not work

		auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("ROLE_STUDENT");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.addFilterBefore(customLoginFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.loginProcessingUrl("/login")
				// .usernameParameter("username")
				// .passwordParameter("password")
				.defaultSuccessUrl("/exercise")
				.failureUrl("/login?error")
				.and()
				.logout()
				.logoutSuccessUrl("/login?logout");
	}

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}