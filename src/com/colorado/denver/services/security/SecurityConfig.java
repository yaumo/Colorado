package com.colorado.denver.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.colorado.denver.services.user.MyUserDetailsService;

//@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Configuration
	@Order(1)
	public static class DocentSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.authorizeRequests()
					.antMatchers("/docent/**").hasRole("DOCENT")
					.and().httpBasic()
					.and().sessionManagement()
					.invalidSessionUrl("/logout")
					.and().addFilterAfter(new BasicAuthenticationFilter(authenticationManagerBean()), BasicAuthenticationFilter.class);

			super.configure(http);
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.debug(true);
		}
	}

	@Configuration
	@Order(2)
	public static class LoginRegistrationSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.authorizeRequests()
					.antMatchers("/registration").permitAll()
					.and().httpBasic()
					.and().sessionManagement().invalidSessionUrl("/logout")
					.and().addFilterAfter(new BasicAuthenticationFilter(authenticationManagerBean()), BasicAuthenticationFilter.class);

			super.configure(http);
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.debug(true);
		}

	}

	@Configuration
	public static class StudentSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.authorizeRequests()
					.antMatchers("/**").hasRole("STUDENT")
					.and().httpBasic()
					.and().sessionManagement().invalidSessionUrl("/logout")
					.and().addFilterAfter(new BasicAuthenticationFilter(authenticationManagerBean()), BasicAuthenticationFilter.class);

			super.configure(http);
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.debug(true);
		}
	}

	/*
	 * @Override
	 * protected void configure(HttpSecurity http) throws Exception {
	 * 
	 * http.csrf().disable()
	 * .authorizeRequests()
	 * .antMatchers("/registration").permitAll()
	 * .antMatchers("/docent/**").hasRole("DOCENT")
	 * .antMatchers("/**").hasRole("STUDENT")
	 * // .antMatchers("/docent/**").hasRole("ADMIN")
	 * .anyRequest().authenticated()
	 * .and().httpBasic()
	 * .and().sessionManagement()
	 * .invalidSessionUrl("/logout")
	 * .and().addFilterAfter(new BasicAuthenticationFilter(authenticationManagerBean()), BasicAuthenticationFilter.class);
	 * 
	 * super.configure(http);
	 * // .authorizeRequests()
	 * // .anyRequest().authenticated()
	 * // .formLogin()
	 * // .loginPage("/login")
	 * // .permitAll()
	 * // .loginProcessingUrl("/login")
	 * // .usernameParameter("username")
	 * // .passwordParameter("password")
	 * // .defaultSuccessUrl("/exercise")
	 * // .successHandler(customSuccessHandler)
	 * // .failureUrl("/login?error")
	 * // .and()
	 * // .logout()
	 * // .logoutSuccessUrl("/login?logout");
	 * 
	 * // http.csrf().disable().authorizeRequests()
	 * //
	 * // .anyRequest()
	 * // .authenticated()
	 * // .and()
	 * // .formLogin()
	 * // .loginPage("/login")
	 * // .permitAll()
	 * // .successHandler(successHandler);
	 * 
	 * // .addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class);
	 * }
	 */

	@Autowired
	MyUserDetailsService userDetailsService;

	protected void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(12);
	}

}