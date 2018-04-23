package com.bis.sourcelead.dto;

import java.io.Serializable;

public class NotificationDTO implements Serializable{
	
	private String firstName;
	private String lastName;
	private String userType;
	private String fileUrl;
	private String body;
	private String calDays;
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
	public String getUserType()
	{
		return userType;
	}
	public void setUserType(String userType)
	{
		this.userType = userType;
	}
	public String getFileUrl()
	{
		return fileUrl;
	}
	public void setFileUrl(String fileUrl)
	{
		this.fileUrl = fileUrl;
	}
	public String getBody()
	{
		return body;
	}
	public void setBody(String body)
	{
		this.body = body;
	}
	public String getCalDays()
	{
		return calDays;
	}
	public void setCalDays(String calDays)
	{
		this.calDays = calDays;
	}
	
	
	
}
