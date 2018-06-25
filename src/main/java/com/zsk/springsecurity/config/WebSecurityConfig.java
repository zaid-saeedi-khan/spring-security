package com.zsk.springsecurity.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.zsk.springsecurity.config.authprovider.UsernamePasswordAuthProvider;

/**
 * 
 * @author Zaid Khan
 *
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource(name = "usernamePasswordAuthProvider")
	private UsernamePasswordAuthProvider usernamePasswordAuthProvider;

	@Resource(name = "googleAuthSuccessHandler")
	private AuthenticationSuccessHandler googleAuthSuccessHandler;

	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(usernamePasswordAuthProvider);
	}

	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.csrf()
				.disable()
			.authorizeRequests()
				.anyRequest()
					.authenticated()
				.and()
			.formLogin()
				.and()
			.oauth2Login().
				successHandler(googleAuthSuccessHandler);
		// @formatter:on
	}

}