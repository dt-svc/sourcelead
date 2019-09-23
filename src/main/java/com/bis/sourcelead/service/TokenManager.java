package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.userdetails.UserDetails;

import com.bis.sourcelead.entity.TokenInfo;

/**
 * Manages tokens - separated from {@link AuthenticationService},
 * so we can implement and plug various policies.
 */
public interface TokenManager {

	/**
	 * Creates a new token for the user and returns its {@link TokenInfo}.
	 * It may add it to the token list or replace the previous one for the user. Never returns {@code null}.
	 */
	TokenInfo createNewToken(UserDetails userDetails,HttpServletRequest request,UserService userService,UserRoleService userRoleService,CandidateService candidateService);

	/** Removes all tokens for user. */
	void removeUserDetails(UserDetails userDetails,HttpSession session);

	/** Removes a single token. */
	UserDetails removeToken(String token,HttpSession session);

	/** Returns user details for a token. */
	UserDetails getUserDetails(String token,HttpSession session);

	/** Returns a collection with token information for a particular user. */
	Collection<TokenInfo> getUserTokens(UserDetails userDetails,HttpSession session);

	/** Returns a map from valid tokens to users. */
	Map<String, UserDetails> getValidUsers(HttpSession session);
}
