package com.bis.sourcelead.dto;

import java.util.List;

public class SLSessionDTO {

	private String sessionId;
	
	private String candidateId;
	
	private String isGlobalAccess;
	
	private String loginUserRole;
	
	private OrgLocationsSessionDTO orgLocations;
	
	private UserSessionDTO user;
	
	private OrgSessionDTO org;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getIsGlobalAccess() {
		return isGlobalAccess;
	}

	public void setIsGlobalAccess(String isGlobalAccess) {
		this.isGlobalAccess = isGlobalAccess;
	}

	public String getLoginUserRole() {
		return loginUserRole;
	}

	public void setLoginUserRole(String loginUserRole) {
		this.loginUserRole = loginUserRole;
	}

	public OrgLocationsSessionDTO getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocationsSessionDTO orgLocations) {
		this.orgLocations = orgLocations;
	}

	public UserSessionDTO getUser() {
		return user;
	}

	public void setUser(UserSessionDTO user) {
		this.user = user;
	}

	public OrgSessionDTO getOrg() {
		return org;
	}

	public void setOrg(OrgSessionDTO org) {
		this.org = org;
	}

	
}
