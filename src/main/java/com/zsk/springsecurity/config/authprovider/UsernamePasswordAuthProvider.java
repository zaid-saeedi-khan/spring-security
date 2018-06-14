package com.zsk.springsecurity.config.authprovider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 
 * @author Zaid Khan
 *
 */
@Component("usernamePasswordAuthProvider")
public class UsernamePasswordAuthProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		Assert.notNull(authentication, "No authentication data provided");
		String username = (String) authentication.getPrincipal();
		if (username == null || username.trim().isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException("Username missing.");
		}
		String password = (String) authentication.getCredentials();
		if (password == null || password.trim().isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException("Password missing.");
		}

		if (!username.equals(password)) {
			throw new BadCredentialsException("Invalid auth credentials.");
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		return new UsernamePasswordAuthenticationToken(username, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}