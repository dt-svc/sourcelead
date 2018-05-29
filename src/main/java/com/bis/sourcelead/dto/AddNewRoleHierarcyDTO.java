package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;

public class AddNewRoleHierarcyDTO {
	
	private Long hierarchyId;
	
	@Required(desc="level")
	private String level;

	public String getLevel()
	{
		return level;
	}

	public void setLevel(String level)
	{
		this.level = level;
	}

	public Long getHierarchyId()
	{
		return hierarchyId;
	}

	public void setHierarchyId(Long hierarchyId)
	{
		this.hierarchyId = hierarchyId;
	}


	
}
