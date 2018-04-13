package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;

public class AddNewRoleDTO {
	
	@Required(desc="roleName")
	private String roleName;
	
	public String getRoleName()
	{
		return roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	public String getLevel()
	{
		return level;
	}

	public void setLevel(String level)
	{
		this.level = level;
	}

	@Required(desc="level")
	private String level;
	
}
