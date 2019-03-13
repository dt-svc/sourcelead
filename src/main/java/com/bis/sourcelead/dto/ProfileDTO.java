package com.bis.sourcelead.dto;

import java.util.List;

public class ProfileDTO {
	private String firstName;
	private String lastName;
	private String location;
	private String mobileNumber;
	private String designation;
	private String email;
	private String linkedinURL;
	private String twitterURL;
	private String facebookURL;
	private String googlePlusURL;
	private String imageURL;
	private List<String> ipaddresses;
	
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
	public String getLocation()
	{
		return location;
	}
	public void setLocation(String location)
	{
		this.location = location;
	}
	public String getMobileNumber()
	{
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber)
	{
		this.mobileNumber = mobileNumber;
	}
	public String getDesignation()
	{
		return designation;
	}
	public void setDesignation(String designation)
	{
		this.designation = designation;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getLinkedinURL()
	{
		return linkedinURL;
	}
	public void setLinkedinURL(String linkedinURL)
	{
		this.linkedinURL = linkedinURL;
	}
	public String getTwitterURL()
	{
		return twitterURL;
	}
	public void setTwitterURL(String twitterURL)
	{
		this.twitterURL = twitterURL;
	}
	public String getFacebookURL()
	{
		return facebookURL;
	}
	public void setFacebookURL(String facebookURL)
	{
		this.facebookURL = facebookURL;
	}
	public String getGooglePlusURL()
	{
		return googlePlusURL;
	}
	public void setGooglePlusURL(String googlePlusURL)
	{
		this.googlePlusURL = googlePlusURL;
	}
	public String getImageURL()
	{
		return imageURL;
	}
	public void setImageURL(String imageURL)
	{
		this.imageURL = imageURL;
	}
	public List<String> getIpaddresses()
	{
		return ipaddresses;
	}
	public void setIpaddresses(List<String> ipaddresses)
	{
		this.ipaddresses = ipaddresses;
	}
	
}
