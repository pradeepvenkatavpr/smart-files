package org.smartfiles.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class AuthenticatedUser implements Authentication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4932354264189877085L;
	private String userName;
	private boolean authenticated = true;
	private List<SimpleGrantedAuthority> roles;

	public AuthenticatedUser(String userName, List<SimpleGrantedAuthority> roles) {
		this.userName = userName;
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public Object getCredentials() {
		return this.userName;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.userName;
	}

	@Override
	public boolean isAuthenticated() {
		return this.authenticated;
	}

	@Override
	public void setAuthenticated(boolean b) throws IllegalArgumentException {
		this.authenticated = b;
	}

	@Override
	public String getName() {
		return this.userName;
	}

}
