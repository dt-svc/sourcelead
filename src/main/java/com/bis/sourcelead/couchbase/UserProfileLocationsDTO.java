package com.bis.sourcelead.couchbase;

import java.io.Serializable;
import java.util.List;

public class UserProfileLocationsDTO implements Serializable{

private static final long serialVersionUID = 1L;
	
	private String orgId;
	
	private String orgName;
	
	private String orgCode;
	
	private String orgLocationId;
	
	private String orgLocationName;
	
	private String locationCode;
	
	private String location;
	
	private String designation; 
	
	private String roleId;
	
	private String roleName;
	
	private String isGlobalAccess;
	
	private String candidateId;
	
	private String userState;
	
	private String userType;
	
	List<String> menuList;
	
	List<String> rolesList;
	
	private String dateFormat;
	
	private String timeZoneId;
	
	private String country;
	
	private String isSuperUser;
	
	private String isSuperAdmin;
	
	private String orgImageUrl;
	
	private String roleHierarchyId;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getOrgLocationName() {
		return orgLocationName;
	}

	public void setOrgLocationName(String orgLocationName) {
		this.orgLocationName = orgLocationName;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
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

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
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

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getTimeZoneId() {
		return timeZoneId;
	}

	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIsGlobalAccess() {
		return isGlobalAccess;
	}

	public void setIsGlobalAccess(String isGlobalAccess) {
		this.isGlobalAccess = isGlobalAccess;
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

	public String getOrgImageUrl() {
		return orgImageUrl;
	}

	public void setOrgImageUrl(String orgImageUrl) {
		this.orgImageUrl = orgImageUrl;
	}

	public String getRoleHierarchyId() {
		return roleHierarchyId;
	}

	public void setRoleHierarchyId(String roleHierarchyId) {
		this.roleHierarchyId = roleHierarchyId;
	}

}
