package com.bis.sourcelead.dto;

import java.util.Date;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public class CampaignDTO {
	private Long id;
	
	private OrgLocations orgLocation;
	
	private String campaignSubject;
	
	private String campaignTitle;
	
	private String campaignCategory;
	
	private String listIds;
	
	private String senderId;
	
	private String campaignId;
	
	private String ipPool;
	
	private String htmlContent;
	
	private String plainContent;
	
	private String campaignScheduleId;
	
	private String campaignScheduleStaus;
	
	private int status;
	
	private Date createdDate;

	private User createdBy;

	private Date updatedDate;

	private String updatedBy;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public OrgLocations getOrgLocation()
	{
		return orgLocation;
	}

	public void setOrgLocation(OrgLocations orgLocation)
	{
		this.orgLocation = orgLocation;
	}

	public String getCampaignSubject()
	{
		return campaignSubject;
	}

	public void setCampaignSubject(String campaignSubject)
	{
		this.campaignSubject = campaignSubject;
	}

	public String getCampaignTitle()
	{
		return campaignTitle;
	}

	public void setCampaignTitle(String campaignTitle)
	{
		this.campaignTitle = campaignTitle;
	}

	public String getCampaignCategory()
	{
		return campaignCategory;
	}

	public void setCampaignCategory(String campaignCategory)
	{
		this.campaignCategory = campaignCategory;
	}

	public String getListIds()
	{
		return listIds;
	}

	public void setListIds(String listIds)
	{
		this.listIds = listIds;
	}

	public String getSenderId()
	{
		return senderId;
	}

	public void setSenderId(String senderId)
	{
		this.senderId = senderId;
	}

	public String getCampaignId()
	{
		return campaignId;
	}

	public void setCampaignId(String campaignId)
	{
		this.campaignId = campaignId;
	}

	public String getIpPool()
	{
		return ipPool;
	}

	public void setIpPool(String ipPool)
	{
		this.ipPool = ipPool;
	}

	public String getHtmlContent()
	{
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent)
	{
		this.htmlContent = htmlContent;
	}

	public String getPlainContent()
	{
		return plainContent;
	}

	public void setPlainContent(String plainContent)
	{
		this.plainContent = plainContent;
	}

	public String getCampaignScheduleId()
	{
		return campaignScheduleId;
	}

	public void setCampaignScheduleId(String campaignScheduleId)
	{
		this.campaignScheduleId = campaignScheduleId;
	}

	public String getCampaignScheduleStaus()
	{
		return campaignScheduleStaus;
	}

	public void setCampaignScheduleStaus(String campaignScheduleStaus)
	{
		this.campaignScheduleStaus = campaignScheduleStaus;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public User getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(User createdBy)
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
	
	
}
