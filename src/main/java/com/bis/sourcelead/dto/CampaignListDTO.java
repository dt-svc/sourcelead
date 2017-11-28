package com.bis.sourcelead.dto;

import java.util.Date;
import java.util.List;

public class CampaignListDTO {
	
	private long id;
	
	private long orgLocationId;
	
	private String listId;
	
	private String listName;
	
	private int status;
	
	private String createdDate;

	private String createdBy;

	private Date updatedDate;

	private String updatedBy;
	
	private String cpnListName;
	
	private List<String> recipientEmail;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(long orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public String getListId()
	{
		return listId;
	}

	public void setListId(String listId)
	{
		this.listId = listId;
	}

	public String getListName()
	{
		return listName;
	}

	public void setListName(String listName)
	{
		this.listName = listName;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(String createdDate)
	{
		this.createdDate = createdDate;
	}

	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy()
	{
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public String getCpnListName()
	{
		return cpnListName;
	}

	public void setCpnListName(String cpnListName)
	{
		this.cpnListName = cpnListName;
	}

	public List<String> getRecipientEmail()
	{
		return recipientEmail;
	}

	public void setRecipientEmail(List<String>  recipientEmail)
	{
		this.recipientEmail = recipientEmail;
	}
	
//	private Set<CampaignRecipient> campaignRecipient;
}
