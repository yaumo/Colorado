package com.colorado.denver.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.colorado.denver.services.SecurityServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityServiceImpl customAuthenticationProvider;

	@Autowired
	DDAuthenticationSuccessHandler successHandler;

	private SecurityAuthenticationSuccessHandler customSuccessHandler = new SecurityAuthenticationSuccessHandler();

	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.authenticationProvider(customAuthenticationProvider);// This will probaply not work
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// .authorizeRequests()
		// .anyRequest().authenticated()
		// .formLogin()
		// .loginPage("/login")
		// .permitAll()
		// .loginProcessingUrl("/login")
		// .usernameParameter("username")
		// .passwordParameter("password")
		// .defaultSuccessUrl("/exercise")
		// .successHandler(customSuccessHandler)
		// .failureUrl("/login?error")
		// .and()
		// .logout()
		// .logoutSuccessUrl("/login?logout");

		http.csrf().disable().authorizeRequests()

				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.successHandler(successHandler);

		http.addFilterBefore(new BasicAuthenticationFilter(authenticationManagerBean()), BasicAuthenticationFilter.class)
				.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class);
	}

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}