package com.bis.sourcelead.dashboard.dto;

import com.bis.sourcelead.annotations.Required;

public class CustomDashBoardConfDTO {
	
	 @Required(desc="goalId")
     private String dashBoardName;
     
     private String description;
     
     private String applicableFor;
     
     private String roles;
     
     private String associates;
     
     private String customDashBoardConfId; 
     
     private String numberOfComponents;
     
     private String customDashBoardConfUUID;
     

	public String getDashBoardName()
	{
		return dashBoardName;
	}

	public String getDescription()
	{
		return description;
	}

	public String getApplicableFor()
	{
		return applicableFor;
	}

	public String getRoles()
	{
		return roles;
	}

	public String getAssociates()
	{
		return associates;
	}

	public void setDashBoardName(String dashBoardName)
	{
		this.dashBoardName = dashBoardName;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setApplicableFor(String applicableFor)
	{
		this.applicableFor = applicableFor;
	}

	public void setRoles(String roles)
	{
		this.roles = roles;
	}

	public void setAssociates(String associates)
	{
		this.associates = associates;
	}

	public String getCustomDashBoardConfId()
	{
		return customDashBoardConfId;
	}

	public void setCustomDashBoardConfId(String customDashBoardConfId)
	{
		this.customDashBoardConfId = customDashBoardConfId;
	}

	public String getNumberOfComponents()
	{
		return numberOfComponents;
	}

	public void setNumberOfComponents(String numberOfComponents)
	{
		this.numberOfComponents = numberOfComponents;
	}

	public String getCustomDashBoardConfUUID()
	{
		return customDashBoardConfUUID;
	}

	public void setCustomDashBoardConfUUID(String customDashBoardConfUUID)
	{
		this.customDashBoardConfUUID = customDashBoardConfUUID;
	}
     
     
}
