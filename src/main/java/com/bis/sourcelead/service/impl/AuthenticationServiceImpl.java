package com.bis.sourcelead.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.SourceleadUserDetailsService;
import com.bis.sourcelead.entity.TokenInfo;
import com.bis.sourcelead.service.AuthenticationService;
import com.bis.sourcelead.service.TokenManager;

/**
 * Service responsible for all around authentication, token checks, etc.
 * This class does not care about HTTP protocol at all.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private  AuthenticationManager authenticationManager;
	
	@Autowired
	private  TokenManager tokenManager;

    @Autowired
    private SourceleadUserDetailsService sourceleadUserDetailsService;
	 
	@PostConstruct
	public void init() {}
	

	@Override
	public TokenInfo authenticate(String login, String password) throws AuthenticationException {
		Authentication authentication = new UsernamePasswordAuthenticationToken(login, password);
			authentication = authenticationManager.authenticate(authentication);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			if (authentication.getPrincipal() != null) {
				String userName =  (String) authentication.getPrincipal();
				TokenInfo newToken = tokenManager.createNewToken(sourceleadUserDetailsService.loadUserByUsername(userName));
				if (newToken == null) {
					return null;
				}
				return newToken;
			}
			return null;	
	}

	@Override
	public boolean checkToken(String token) {
		UserDetails userDetails = tokenManager.getUserDetails(token);
		if (userDetails == null) {
			return false;
		}

		Authentication securityToken = new PreAuthenticatedAuthenticationToken(
			userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(securityToken);

		return true;
	}

	@Override
	public void logout(String token) {
		UserDetails logoutUser = tokenManager.removeToken(token);
		SecurityContextHolder.clearContext();
	}

	@Override
	public UserDetails currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		return (UserDetails) authentication.getPrincipal();
	}
}
