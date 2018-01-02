package com.bis.sourcelead.dto;

public class UserRoleDTO {
	private Long id;
	private String userName;
	private Long userId;
	private String userRoleName;
	private String isMailSent;
	private String userEmail;
	private String userCode;
	private String fileUrl;
	private String mobilePhone;
	private String reportingManagerName;
	private String employeMentType;
	private String userState;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserRoleName() {
		return userRoleName;
	}
	public String getIsMailSent() {
		return isMailSent;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public String getUserCode() {
		return userCode;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public String getReportingManagerName() {
		return reportingManagerName;
	}
	public String getEmployeMentType() {
		return employeMentType;
	}
	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}
	public void setIsMailSent(String isMailSent) {
		this.isMailSent = isMailSent;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public void setReportingManagerName(String reportingManagerName) {
		this.reportingManagerName = reportingManagerName;
	}
	public void setEmployeMentType(String employeMentType) {
		this.employeMentType = employeMentType;
	}
	public String getUserState() {
		return userState;
	}
	public void setUserState(String userState) {
		this.userState = userState;
	}
}
