package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OtpVerificationDTO  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	
	private String username;
	
	private String password;
	
	private Long userRoleId;
	
	private String emailId;
	
	private String mobilePhone;
	
	private String authToken;
	
	private String userLoginSecurity;
	
	private String status;
	
	private String sourceleadUrl;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String isUserLoginSecurity() {
		return userLoginSecurity;
	}

	public void setUserLoginSecurity(String userLoginSecurity) {
		this.userLoginSecurity = userLoginSecurity;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSourceleadUrl() {
		return sourceleadUrl;
	}

	public void setSourceleadUrl(String sourceleadUrl) {
		this.sourceleadUrl = sourceleadUrl;
	}

	public String getUserLoginSecurity() {
		return userLoginSecurity;
	}
	
}
