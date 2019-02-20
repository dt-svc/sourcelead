package com.bis.sourcelead.couchbase;

import java.io.Serializable;

public class UserAccountsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String roleName;
	
	private String displayName;
	
	private String orgLocationId;
	
	private String userRoleId;
	
	private String loginSecurity;
	
	private String orgLocationLoginSecurity;
	
	private String userId;
	
	private String orgImgUrl;
	
	private String orgLocationName;
	
	private String orgName;
	
	private String country;
	
	private String state;
	
	private String city;
	
	private String designation;
	
	private String userType;
	
	

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getLoginSecurity() {
		return loginSecurity;
	}

	public void setLoginSecurity(String loginSecurity) {
		this.loginSecurity = loginSecurity;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgImgUrl() {
		return orgImgUrl;
	}

	public void setOrgImgUrl(String orgImgUrl) {
		this.orgImgUrl = orgImgUrl;
	}

	public String getOrgLocationName() {
		return orgLocationName;
	}

	public void setOrgLocationName(String orgLocationName) {
		this.orgLocationName = orgLocationName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgLocationLoginSecurity() {
		return orgLocationLoginSecurity;
	}

	public void setOrgLocationLoginSecurity(String orgLocationLoginSecurity) {
		this.orgLocationLoginSecurity = orgLocationLoginSecurity;
	}
}
