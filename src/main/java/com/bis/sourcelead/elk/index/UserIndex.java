package com.bis.sourcelead.elk.index;

import java.io.Serializable;

public class UserIndex implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long userId;
	
	private Long userRoleTableId;

	private String employeeId;

	private Long orgLocationId;

	private String firstName; 

	private String lastName;
 
	private String email; 

	private String mobilePhone;
	
	private String extensionNumber;

	private String userType;

	private String roleName;
	
	private String middleName;

	private String gender;
	
	private String maritalStatus;

	private String dateofBirth;

	private String statusType;

	private String terminateReason;

	private String status;
	
	private String hireDate;
	
	private String rate;
	
	private String ratePer;

	private String createdDate;

	private String createdBy;

	private String updatedDate;

	private String updatedBy;
	
	private String userStatus;
	
	private String isMailSent;
	
	private String userCode;
	
	private Long reportingManagerId;
	
	private String userRoleId;

	private String searchType;
	
	
	
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getExtensionNumber()
	{
		return extensionNumber;
	}

	public void setExtensionNumber(String extensionNumber)
	{
		this.extensionNumber = extensionNumber;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}


	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	public String getTerminateReason() {
		return terminateReason;
	}

	public void setTerminateReason(String terminateReason) {
		this.terminateReason = terminateReason;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getIsMailSent() {
		return isMailSent;
	}

	public void setIsMailSent(String isMailSent) {
		this.isMailSent = isMailSent;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRatePer() {
		return ratePer;
	}

	public void setRatePer(String ratePer) {
		this.ratePer = ratePer;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public Long getReportingManagerId() {
		return reportingManagerId;
	}

	public void setReportingManagerId(Long reportingManagerId) {
		this.reportingManagerId = reportingManagerId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUserRoleTableId() {
		return userRoleTableId;
	}

	public void setUserRoleTableId(Long userRoleTableId) {
		this.userRoleTableId = userRoleTableId;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	
}
