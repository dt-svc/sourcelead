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
	
	
}
