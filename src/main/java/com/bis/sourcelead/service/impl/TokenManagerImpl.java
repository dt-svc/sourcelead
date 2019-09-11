package com.bis.sourcelead.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.dto.UserDetailsDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.TokenInfo;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.TokenManager;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Implements simple token manager, that keeps a single token for each user. If user logs in again,
 * older token is invalidated.
 */
@Service
public class TokenManagerImpl implements TokenManager {

	//private Map<String, UserDetails> validUsers = new HashMap<>();
	/**
	 * This maps system users to tokens because equals/hashCode is delegated to User entity.
	 * This can store either one token or list of them for each user, depending on what you want to do.
	 * Here we store single token, which means, that any older tokens are invalidated.
	 */
	//private Map<UserDetails, TokenInfo> tokens = new HashMap<>();

	@Override
	public TokenInfo createNewToken(UserDetails userDetails,HttpServletRequest request,UserService userService,UserRoleService userRoleService,CandidateService candidateService,UserStatsCacheHelper userStatsCacheHelper) {
		//HttpSession session = request.getSession();
		/*Map<UserDetails, TokenInfo> tokens = (Map<UserDetails, TokenInfo>) session.getAttribute("tokens");
		if(validUsers == null){
			validUsers = new HashMap<>();
		}
		if(tokens == null){
			tokens = new HashMap<>();
		}*/
		
		String token = generateToken();;
		TokenInfo tokenInfo = new TokenInfo(token, userDetails);
		/*Collection<TokenInfo> tokenInfoList = null;
		TokenInfo tokenInfo = null;
		if(tokens != null && !tokens.isEmpty()){
			tokenInfoList = getUserTokens(userDetails,session);
		}
		
		if(tokenInfoList != null && !tokenInfoList.isEmpty() && !tokenInfoList.contains(null)){
			tokenInfo = tokenInfoList.iterator().next();
			token = tokenInfo.getToken();
		}
		else{*/
		/*	do {
				token = generateToken();
			} while (validUsers.containsKey(token));
			 tokenInfo = new TokenInfo(token, userDetails);
			//removeUserDetails(userDetails);
			UserDetails previous = validUsers.put(token, userDetails);
			if (previous != null) {
				System.out.println(" *** SERIOUS PROBLEM HERE - we generated the same token (randomly?)!");
				return null;
			}*/
		    UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
		    userDetailsDTO.setUsername(userDetails.getUsername());
		    userDetailsDTO.setPassword(userDetails.getPassword());
		    if(userDetails.getAuthorities() != null) {
		    	List<String> roles = userDetails.getAuthorities().stream().map(ga -> ga.getAuthority()).collect(Collectors.toList());
		    	userDetailsDTO.setRoles(roles);
		    }
			userStatsCacheHelper.saveLoginUserSessionStoreToCouchbaseForMobiles(token, userDetailsDTO, request);
			//tokens.put(userDetails, tokenInfo);
			//(user.getId());
			User user = userService.findByUserName(userDetails.getUsername());
			
			UserRole userRole;
			try {
				userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
				OrgLocations orgLocations = userRole.getOrgLocations();
				if(orgLocations.getTimezone() != null) {
					orgLocations.setTimeZoneId(orgLocations.getTimezone().getId() + "");
				}
				SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocations);
				Candidate candidate = candidateService.getCandidateByConsultantUserId(user.getId(), userRole.getOrgLocations().getId());
				SourceLeadSessionManager.setSessionAttribute(request, "candidate", candidate);
				SourceLeadSessionManager.setSessionAttribute(request, "org", orgLocations.getOrg());
				SourceLeadSessionManager.setSessionAttribute(request, "orgId", orgLocations.getOrg().getId());
				SourceLeadSessionManager.setSessionAttribute(request, "orgLocationId", orgLocations.getId());
				SourceLeadSessionManager.setSessionAttribute(request, "userId", user.getId());
				SourceLeadSessionManager.setSessionAttribute(request, "userRoleId", userRole.getId());
			} catch (Exception e) {}
			SourceLeadSessionManager.setSessionAttribute(request, "user", user);
		//}

		
		//session.setAttribute("validUsers", validUsers);
	   //	session.setAttribute("tokens", tokens);
		return tokenInfo;
	}

	private String generateToken() {
		byte[] tokenBytes = new byte[32];
		new SecureRandom().nextBytes(tokenBytes);
		return new String(Base64.encode(tokenBytes), StandardCharsets.UTF_8);
	}

	@Override
	public void removeUserDetails(UserDetails userDetails,HttpSession session) {
		Map<String, UserDetails> validUsers = (Map<String, UserDetails>) session.getAttribute("validUsers");
		Map<UserDetails, TokenInfo> tokens = (Map<UserDetails, TokenInfo>) session.getAttribute("tokens");
		TokenInfo token = tokens.remove(userDetails);
		if (token != null) {
			validUsers.remove(token.getToken());
		}
	}

	@Override
	public UserDetails removeToken(String token,HttpSession session) {
		Map<String, UserDetails> validUsers = (Map<String, UserDetails>) session.getAttribute("validUsers");
		Map<UserDetails, TokenInfo> tokens = (Map<UserDetails, TokenInfo>) session.getAttribute("tokens");
		UserDetails userDetails = validUsers.remove(token);
		if (userDetails != null) {
			tokens.remove(userDetails);
		}
		return userDetails;
	}

	@Override
	public UserDetails getUserDetails(String token,UserStatsCacheHelper userStatsCacheHelper) {
		UserDetailsDTO userDetailsDTO =  userStatsCacheHelper.getUserDetails(token);
		return new org.springframework.security.core.userdetails.User(userDetailsDTO.getUsername(), userDetailsDTO.getPassword(), true, true, true, true, buildUserAuthority(userDetailsDTO.getRoles()));
	}

	/*@Override
	public Collection<TokenInfo> getUserTokens(UserDetails userDetails,HttpSession session) {
		Map<UserDetails, TokenInfo> tokens = (Map<UserDetails, TokenInfo>) session.getAttribute("tokens");
		return Arrays.asList(tokens.get(userDetails));
	}*/

	@Override
	public Map<String, UserDetails> getValidUsers(HttpSession session) {
		Map<String, UserDetails> validUsers = (Map<String, UserDetails>) session.getAttribute("validUsers");
		return Collections.unmodifiableMap(validUsers);
	}
	
	
	private List<GrantedAuthority> buildUserAuthority(List<String> roles) {
    	Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
    	if(roles != null && !roles.isEmpty()){
    		for (String role : roles) {
    			setAuths.add(new SimpleGrantedAuthority(role));
    		}
    	}
    	List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
    	return Result;
   }
}
