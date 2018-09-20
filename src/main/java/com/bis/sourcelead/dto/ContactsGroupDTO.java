package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;

public class ContactsGroupDTO {

	 private Long id;
	 
	 private String groupId;
	 
	 @Required(desc = "group name")
	 private String groupName;
	 
	 private String createdUser;
	 
	 private String Status;
	 
	 private String createdDate;

	 private String updatedDate;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public String getCreatedUser()
	{
		return createdUser;
	}

	public void setCreatedUser(String createdUser)
	{
		this.createdUser = createdUser;
	}

	public String getStatus()
	{
		return Status;
	}

	public void setStatus(String status)
	{
		Status = status;
	}

	public String getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(String createdDate)
	{
		this.createdDate = createdDate;
	}

	public String getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public String getGroupId()
	{
		return groupId;
	}

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	} 
	 
	
}
