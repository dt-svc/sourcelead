package com.bis.sourcelead.dashboard.dto;

import com.bis.sourcelead.annotations.Required;

public class ModuleConfigureDTO {
	
	@Required(desc="subModuleIds")
	private String subModuleIds;
	
	private String customDashBoardConfUUID;

	public String getSubModuleIds()
	{
		return subModuleIds;
	}

	public void setSubModuleIds(String subModuleIds)
	{
		this.subModuleIds = subModuleIds;
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
