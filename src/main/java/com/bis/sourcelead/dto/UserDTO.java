package com.bis.sourcelead.dto;

import java.io.Serializable;

public class UserDTO extends SearchDTO implements Serializable {
	private long id;
	private String name;
	private String designation;
	private String profileImage;
	private long parentId;
	private String userType;
	private String firstName;
	private String lastName;
	private String mobilePhone;
	private String userRole;
	private String searchByName;
	private String userState;
	private String loginUserRole;
	private String roleId;
	
	public long getId()
	{
		return id;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDesignation()
	{
		return designation;
	}
	
	public void setDesignation(String designation)
	{
		this.designation = designation;
	}
	
	public String getProfileImage()
	{
		return profileImage;
	}
	
	public void setProfileImage(String profileImage)
	{
		this.profileImage = profileImage;
	}
	
	public long getParentId()
	{
		return parentId;
	}
	
	public void setParentId(long parentId)
	{
		this.parentId = parentId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getSearchByName() {
		return searchByName;
	}

	public String getUserState() {
		return userState;
	}

	public String getLoginUserRole() {
		return loginUserRole;
	}

	public void setSearchByName(String searchByName) {
		this.searchByName = searchByName;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public void setLoginUserRole(String loginUserRole) {
		this.loginUserRole = loginUserRole;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
