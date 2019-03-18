package com.bis.sourcelead.couchbase;

import java.io.Serializable;

public class LoginUserSessionStoreDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String documentType;
	
	private String lastUpdatedDate;
	
	private String userRoleUUID;
	
	private String orgLocationCode;
	
	private String sessionId;
	
	private String loginUserName;
	
	private String orgLocationName;
	
	private String orgImgUrl;
	
	private String country;
	
	private String city;
	
	private String state;
	
	private String loginUserAccounts;
	
	private String actionUUID;
	
	private String actionId;

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getUserRoleUUID() {
		return userRoleUUID;
	}

	public void setUserRoleUUID(String userRoleUUID) {
		this.userRoleUUID = userRoleUUID;
	}

	public String getOrgLocationCode() {
		return orgLocationCode;
	}

	public void setOrgLocationCode(String orgLocationCode) {
		this.orgLocationCode = orgLocationCode;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getOrgLocationName() {
		return orgLocationName;
	}

	public void setOrgLocationName(String orgLocationName) {
		this.orgLocationName = orgLocationName;
	}

	public String getOrgImgUrl() {
		return orgImgUrl;
	}

	public void setOrgImgUrl(String orgImgUrl) {
		this.orgImgUrl = orgImgUrl;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLoginUserAccounts() {
		return loginUserAccounts;
	}

	public void setLoginUserAccounts(String loginUserAccounts) {
		this.loginUserAccounts = loginUserAccounts;
	}

	public String getActionUUID() {
		return actionUUID;
	}

	public void setActionUUID(String actionUUID) {
		this.actionUUID = actionUUID;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	
	
}
