package com.bis.sourcelead.dto;

import java.math.BigInteger;

import com.bis.sourcelead.annotations.Required;

public class TeamDTO extends TeamMemberDTO{

	@Required(desc="name")
	private String name;
	
	@Required(desc="editOwnerUserId", dependsOn = "tabType", parentValue = "editTeamForm")
	private String editOwnerUserId;
	
	@Required(desc="memberUserId", dependsOn = "tabType", parentValue = "teamForm")
	private String memberUserId;
	
	@Required(desc="ownerUserId", dependsOn = "tabType", parentValue = "teamForm")
	private String ownerUserId;
	
	private String teamId;
	
	private String tabType;
	
	private BigInteger id;
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getEditOwnerUserId()
	{
		return editOwnerUserId;
	}
	public void setEditOwnerUserId(String editOwnerUserId)
	{
		this.editOwnerUserId = editOwnerUserId;
	}
	public String getMemberUserId()
	{
		return memberUserId;
	}
	public void setMemberUserId(String memberUserId)
	{
		this.memberUserId = memberUserId;
	}
	public String getOwnerUserId()
	{
		return ownerUserId;
	}
	public void setOwnerUserId(String ownerUserId)
	{
		this.ownerUserId = ownerUserId;
	}
	public String getTeamId()
	{
		return teamId;
	}
	public void setTeamId(String teamId)
	{
		this.teamId = teamId;
	}
	public String getTabType()
	{
		return tabType;
	}
	public void setTabType(String tabType)
	{
		this.tabType = tabType;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	
}
