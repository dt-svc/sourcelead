package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;

public class OrgPrimaryUserDTO {
	
	@Required(desc="primaryUserFirstName")
	private String primaryUserFirstName;
	
	@Required(desc="primaryUserEmail")
	private String primaryUserEmail;
	
	private String primaryUserLastName;
	
	private String mobilePhone;

	public String getPrimaryUserFirstName()
	{
		return primaryUserFirstName;
	}

	public void setPrimaryUserFirstName(String primaryUserFirstName)
	{
		this.primaryUserFirstName = primaryUserFirstName;
	}

	public String getPrimaryUserEmail()
	{
		return primaryUserEmail;
	}

	public void setPrimaryUserEmail(String primaryUserEmail)
	{
		this.primaryUserEmail = primaryUserEmail;
	}

	public String getPrimaryUserLastName()
	{
		return primaryUserLastName;
	}

	public void setPrimaryUserLastName(String primaryUserLastName)
	{
		this.primaryUserLastName = primaryUserLastName;
	}

	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}
	
	
	
	
	
}
