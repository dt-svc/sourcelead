package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.SourceleadUserDetailsService;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.dto.UserDetailsDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.TokenInfo;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.AuthenticationService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.TokenManager;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

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
    
    @Autowired
    private  UserService userService;
    
    @Autowired
    private UserRoleService userRoleService;
    
    @Autowired
    private CandidateService candidateService;
    
    @Autowired
    private  UserStatsCacheHelper userStatsCacheHelper;
	 
	@PostConstruct
	public void init() {}
	

	@Override
	public TokenInfo authenticate(String login, String password,HttpServletRequest request,HttpSession session) throws AuthenticationException {
		Authentication authentication = new UsernamePasswordAuthenticationToken(login, password);
			try {
				authentication = authenticationManager.authenticate(authentication);
			}catch(BadCredentialsException e) {
				if(!e.getMessage().equalsIgnoreCase("redirect_to_dashboard")) {
					throw new BadCredentialsException(e.getMessage().toString());
				}
			}
			SecurityContextHolder.getContext().setAuthentication(authentication);
			if (authentication.getPrincipal() != null) {
				String userName =  (String) authentication.getPrincipal();
				UserDetails userDetails = sourceleadUserDetailsService.loadUserByUsername(userName);
				   //tokenManager.getUserTokens(userDetails);
				TokenInfo newToken = tokenManager.createNewToken(userDetails,request,userService,userRoleService,candidateService,userStatsCacheHelper);
				if (newToken == null) {
					return null;
				}
				return newToken;
			}
			return null;	
	}
	@Override
	public void getAuthentication(HttpServletRequest request,HttpSession session) throws AuthenticationException {
		Authentication authentication =null;
			try {
				authentication = SecurityContextHolder.getContext().getAuthentication();
			}catch(BadCredentialsException e) {
				if(!e.getMessage().equalsIgnoreCase("redirect_to_dashboard")) {
					throw new BadCredentialsException(e.getMessage().toString());
				}
			}
			SecurityContextHolder.getContext().setAuthentication(authentication);
			if (authentication.getName() != null) {
				String userName =  (String) authentication.getName();
				UserDetails userDetails = sourceleadUserDetailsService.loadUserByUsername(userName);
				 UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
				    userDetailsDTO.setUsername(userDetails.getUsername());
				    userDetailsDTO.setPassword(userDetails.getPassword());
				    if(userDetails.getAuthorities() != null) {
				    	List<String> roles = userDetails.getAuthorities().stream().map(ga -> ga.getAuthority()).collect(Collectors.toList());
				    	userDetailsDTO.setRoles(roles);
				    }
					User user = userService.findByUserName(userDetails.getUsername());
					
					UserRole userRole;
					try {
						userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
						OrgLocations orgLocations = userRole.getOrgLocations();
						if(orgLocations.getTimezone() != null) {
							orgLocations.setTimeZoneId(orgLocations.getTimezone().getId() + "");
						}
						OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
						SourceLeadSessionManager.setSessionAttribute(request, "org", orgLocations.getOrg());
						SourceLeadSessionManager.setSessionAttribute(request, "orgId", orgLocations.getOrg().getId());
						SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocations);
						Candidate candidate = candidateService.getCandidateByConsultantUserId(user.getId(), userRole.getOrgLocations().getId());
						SourceLeadSessionManager.setSessionAttribute(request, "candidate", candidate);
					} catch (Exception e) {}
					SourceLeadSessionManager.setSessionAttribute(request, "user", user);
			}
	}

	@Override
	public boolean checkToken(String token,HttpSession session) {
		UserDetails userDetails = tokenManager.getUserDetails(token,userStatsCacheHelper);
		if (userDetails == null) {
			return false;
		}

		Authentication securityToken = new PreAuthenticatedAuthenticationToken(
			userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(securityToken);

		return true;
	}

	@Override
	public void logout(String token,HttpSession session) {
		/*UserDetails logoutUser = tokenManager.removeToken(token,session);
		session.invalidate();*/
		//remove the token from coutch
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
