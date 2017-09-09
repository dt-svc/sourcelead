package com.bis.sourcelead.dto;

public class TeamDTO extends TeamMemberDTO{

	private String name;
	private String editOwnerUserId;
	private String memberUserId;
	private String ownerUserId;
	private String teamId;
	//private String teamMemberUserId;
	
	
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
	
}
