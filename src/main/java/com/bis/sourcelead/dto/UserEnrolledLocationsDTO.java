package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UserEnrolledLocationsDTO  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private boolean alreadyVerifiedBefore;
	
	private String username;
	
	private String requirementUUID;
	
	private String sourceleadUrl;

	private List<UserSpocRolesDTO> userSpocRolesList;

	public  boolean getAlreadyVerifiedBefore() {
		return alreadyVerifiedBefore;
	}

	public void setAlreadyVerifiedBefore(boolean alreadyVerifiedBefore) {
		this.alreadyVerifiedBefore = alreadyVerifiedBefore;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRequirementUUID() {
		return requirementUUID;
	}

	public void setRequirementUUID(String requirementUUID) {
		this.requirementUUID = requirementUUID;
	}

	public String getSourceleadUrl() {
		return sourceleadUrl;
	}

	public void setSourceleadUrl(String sourceleadUrl) {
		this.sourceleadUrl = sourceleadUrl;
	}

	public List<UserSpocRolesDTO> getUserSpocRolesList() {
		return userSpocRolesList;
	}

	public void setUserSpocRolesList(List<UserSpocRolesDTO> userSpocRolesList) {
		this.userSpocRolesList = userSpocRolesList;
	}
	
	
	
}
