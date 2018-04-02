package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.entity.User;

@SuppressWarnings("serial")
public class OrgDTO extends SearchDTO implements Serializable{

	private Long id;
	
	private  User user;
	
	private Long userId;
	
	private String email;
	
	private String orgName;
	
	private String isMailSent;

	private String userState;
	
	private String mobilePhone;
	
	private Long registeredUserId;
	
	private String location;
	
	private Long primaryOrgLocationId;
	
	private String registrationStatus;
	
	private String locationCode;
	
	private String orgCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getEmail() {
		return email;
	}

	public String getIsMailSent() {
		return isMailSent;
	}

	public String getUserState() {
		return userState;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public Long getRegisteredUserId() {
		return registeredUserId;
	}

	public String getLocation() {
		return location;
	}

	public Long getPrimaryOrgLocationId() {
		return primaryOrgLocationId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setIsMailSent(String isMailSent) {
		this.isMailSent = isMailSent;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setRegisteredUserId(Long registeredUserId) {
		this.registeredUserId = registeredUserId;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setPrimaryOrgLocationId(Long primaryOrgLocationId) {
		this.primaryOrgLocationId = primaryOrgLocationId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRegistrationStatus() {
		return registrationStatus;
	}

	public void setRegistrationStatus(String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
}
