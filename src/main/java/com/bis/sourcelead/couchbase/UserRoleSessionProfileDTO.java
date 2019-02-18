package com.bis.sourcelead.couchbase;

import java.io.Serializable;
import java.util.List;

public class UserRoleSessionProfileDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String documentType;
	
	private String firstName;
	
	private String lastName;
	
	private String middleName;
	
	private String userRoleId;
	
	private String userId;
	
	private String roleId;
	
	private String roleName;
	
	private String roleDisplayName;
	
	private String reportingManagerId;
	
	private String orgLocationId;
	
	private String userState;
	
	private String userType;
	
	private String designation;
	
	private String resourceManagerId;
	
	private String isGlobalUser;
	
	private String shiftId;
	
	private String gradeId;
	
	private String uuid;
	
    List<String> menuList;
	
	List<String> rolesList;
	
	private String lastUpdatedDate;
	
	private String orgLocationCode;
	
	private String isSuperUser;
	
	private String isSuperAdmin;
	
	private String roleHierarchyId;
	
	private String userCode;
	
	private String candidateId;
	
	private String candidateUUID;
	
	private String email;
	
	private String loginUserFullName;
	
	private String imgUrl;
	
	private String mobilePhone;
	
	private String location;
	
	private String linkedIn;
	
	private String facebook;
	
	private String twitter;
	
	private String googlePlus;
	
	private String userLoginSecurity;
	
	private String newlyLoginUser;
	
	private String isPayRollApprover;
	
	private String country;

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDisplayName() {
		return roleDisplayName;
	}

	public void setRoleDisplayName(String roleDisplayName) {
		this.roleDisplayName = roleDisplayName;
	}

	public String getReportingManagerId() {
		return reportingManagerId;
	}

	public void setReportingManagerId(String reportingManagerId) {
		this.reportingManagerId = reportingManagerId;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getResourceManagerId() {
		return resourceManagerId;
	}

	public void setResourceManagerId(String resourceManagerId) {
		this.resourceManagerId = resourceManagerId;
	}

	public String getIsGlobalUser() {
		return isGlobalUser;
	}

	public void setIsGlobalUser(String isGlobalUser) {
		this.isGlobalUser = isGlobalUser;
	}

	public String getShiftId() {
		return shiftId;
	}

	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public List<String> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<String> menuList) {
		this.menuList = menuList;
	}

	public List<String> getRolesList() {
		return rolesList;
	}

	public void setRolesList(List<String> rolesList) {
		this.rolesList = rolesList;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getOrgLocationCode() {
		return orgLocationCode;
	}

	public void setOrgLocationCode(String orgLocationCode) {
		this.orgLocationCode = orgLocationCode;
	}

	public String getIsSuperUser() {
		return isSuperUser;
	}

	public void setIsSuperUser(String isSuperUser) {
		this.isSuperUser = isSuperUser;
	}

	public String getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(String isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public String getRoleHierarchyId() {
		return roleHierarchyId;
	}

	public void setRoleHierarchyId(String roleHierarchyId) {
		this.roleHierarchyId = roleHierarchyId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCandidateUUID() {
		return candidateUUID;
	}

	public void setCandidateUUID(String candidateUUID) {
		this.candidateUUID = candidateUUID;
	}

	public String getLoginUserFullName() {
		return loginUserFullName;
	}

	public void setLoginUserFullName(String loginUserFullName) {
		this.loginUserFullName = loginUserFullName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLinkedIn() {
		return linkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getGooglePlus() {
		return googlePlus;
	}

	public void setGooglePlus(String googlePlus) {
		this.googlePlus = googlePlus;
	}

	public String getUserLoginSecurity() {
		return userLoginSecurity;
	}

	public void setUserLoginSecurity(String userLoginSecurity) {
		this.userLoginSecurity = userLoginSecurity;
	}

	public String getNewlyLoginUser() {
		return newlyLoginUser;
	}

	public void setNewlyLoginUser(String newlyLoginUser) {
		this.newlyLoginUser = newlyLoginUser;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getIsPayRollApprover() {
		return isPayRollApprover;
	}

	public void setIsPayRollApprover(String isPayRollApprover) {
		this.isPayRollApprover = isPayRollApprover;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
