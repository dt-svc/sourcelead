package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UserSpocRolesDTO  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long userRoleId;
	
	private Long userId;
	
	private Long orgLocationsId;
	
	private String orgLocationLoginSecurity;
	
	private String userLoginSecurity;
	
	private String orgFileUrl;
	
	private String orgLocationName;
	
	private String orgLocation;
	
	private String designation;

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrgLocationsId() {
		return orgLocationsId;
	}

	public void setOrgLocationsId(Long orgLocationsId) {
		this.orgLocationsId = orgLocationsId;
	}

	public String getOrgLocationLoginSecurity() {
		return orgLocationLoginSecurity;
	}

	public void setOrgLocationLoginSecurity(String orgLocationLoginSecurity) {
		this.orgLocationLoginSecurity = orgLocationLoginSecurity;
	}

	public String getUserLoginSecurity() {
		return userLoginSecurity;
	}

	public void setUserLoginSecurity(String userLoginSecurity) {
		this.userLoginSecurity = userLoginSecurity;
	}

	public String getOrgFileUrl() {
		return orgFileUrl;
	}

	public void setOrgFileUrl(String orgFileUrl) {
		this.orgFileUrl = orgFileUrl;
	}

	public String getOrgLocationName() {
		return orgLocationName;
	}

	public void setOrgLocationName(String orgLocationName) {
		this.orgLocationName = orgLocationName;
	}

	public String getOrgLocation() {
		return orgLocation;
	}

	public void setOrgLocation(String orgLocation) {
		this.orgLocation = orgLocation;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	
	
	
}
