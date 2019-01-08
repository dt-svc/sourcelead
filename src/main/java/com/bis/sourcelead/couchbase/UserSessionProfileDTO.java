package com.bis.sourcelead.couchbase;

import java.io.Serializable;

public class UserSessionProfileDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String gender;
	
	private String imageUrl;
	
	private String userName;
	
	private String middleName;
	
	private String userCode;
	
	Object userProfileLocationsList;
	


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Object getUserProfileLocationsList() {
		return userProfileLocationsList;
	}

	public void setUserProfileLocationsList(Object userProfileLocationsList) {
		this.userProfileLocationsList = userProfileLocationsList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}
