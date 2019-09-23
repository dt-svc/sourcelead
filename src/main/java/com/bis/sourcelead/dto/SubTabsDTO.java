package com.bis.sourcelead.dto;

public class SubTabsDTO {
	
	private Long subTabId;
	
	private String subTabName;
	
	private String subTabDisplayName;
	
	private String isSubTabConfigured;

	public Long getSubTabId()
	{
		return subTabId;
	}

	public String getSubTabName()
	{
		return subTabName;
	}

	public String getSubTabDisplayName()
	{
		return subTabDisplayName;
	}

	public void setSubTabId(Long subTabId)
	{
		this.subTabId = subTabId;
	}

	public void setSubTabName(String subTabName)
	{
		this.subTabName = subTabName;
	}

	public void setSubTabDisplayName(String subTabDisplayName)
	{
		this.subTabDisplayName = subTabDisplayName;
	}

	public String getIsSubTabConfigured()
	{
		return isSubTabConfigured;
	}

	public void setIsSubTabConfigured(String isSubTabConfigured)
	{
		this.isSubTabConfigured = isSubTabConfigured;
	}
	
	
	
}
