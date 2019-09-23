package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;

public class InviteCompanyDTO {

	@Required(desc="company name")
	private String companyName;
	
	private String location;
	
	private String inviteCountry;
	
	private String inviteSate;
	
	private String inviteCity;
	
	@Required(desc="name")
	private String name;
	
	@Required(desc="email")
	private String email;
	
	private String phoneNumber;

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getInviteCountry()
	{
		return inviteCountry;
	}

	public void setInviteCountry(String inviteCountry)
	{
		this.inviteCountry = inviteCountry;
	}

	public String getInviteSate()
	{
		return inviteSate;
	}

	public void setInviteSate(String inviteSate)
	{
		this.inviteSate = inviteSate;
	}

	public String getInviteCity()
	{
		return inviteCity;
	}

	public void setInviteCity(String inviteCity)
	{
		this.inviteCity = inviteCity;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
}
