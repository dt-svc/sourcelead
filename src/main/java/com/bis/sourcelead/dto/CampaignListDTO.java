package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CampaignListDTO extends SearchDTO implements Serializable{
	
	private long id;
	
	private long orgLocationId;
	
	private String listId;
	
	private String listName;
	
	private int listCount;
	
	private int status;
	
	private String createdDate;

	private String createdBy;

	private Date updatedDate;

	private String updatedBy;
	
	private String cpnListName;
	
	private List<String> recipientEmail;
	
	private String contactListId;
	
	private String campaignType;
	
	private String typeOfCampaign;
	
	private String firstName;
	
	private String lastName;
	
	private String email;

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

	public int getListCount()
	{
		return listCount;
	}

	public void setListCount(int listCount)
	{
		this.listCount = listCount;
	}

	public String getContactListId()
	{
		return contactListId;
	}

	public void setContactListId(String contactListId)
	{
		this.contactListId = contactListId;
	}


	public String getTypeOfCampaign()
	{
		return typeOfCampaign;
	}

	public void setTypeOfCampaign(String typeOfCampaign)
	{
		this.typeOfCampaign = typeOfCampaign;
	}

	public String getCampaignType()
	{
		return campaignType;
	}

	public void setCampaignType(String campaignType)
	{
		this.campaignType = campaignType;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
	
	
	
}
