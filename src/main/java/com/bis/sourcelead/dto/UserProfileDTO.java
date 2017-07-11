package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Map;

public class UserProfileDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	
	private String firstName;
	
	private String lastName;
	
	private String profileImageUrl;
	
	Map<String,OrgLocationDTO> orgLocationsList;

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getProfileImageUrl()
	{
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl)
	{
		this.profileImageUrl = profileImageUrl;
	}

	public Map<String, OrgLocationDTO> getOrgLocationsList()
	{
		return orgLocationsList;
	}

	public void setOrgLocationsList(Map<String, OrgLocationDTO> orgLocationsList)
	{
		this.orgLocationsList = orgLocationsList;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
