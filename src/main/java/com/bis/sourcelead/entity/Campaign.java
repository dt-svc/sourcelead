package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "CAMPAIGN")
public class Campaign {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGLOCATION_ID", nullable = false)
	private OrgLocations orgLocation;
	
	@Column(name = "CAMPAIGN_SUBJECT")
	private String campaignSubject;
	
	@Column(name = "CAMPAIGN_TITLE")
	private String campaignTitle;
	
	@Column(name = "CAMPAIGN_CATEGORY")
	private String campaignCategory;
	
	@Column(name = "LIST_IDS")
	private String listIds;
	
	@Column(name = "SENDER_ID")
	private String senderId;
	
	@Column(name = "CAMPAIGN_ID")
	private String campaignId;
	
	@Column(name = "CAMPAIGN_TYPE")
	private int campaignType;
	
	@Column(name = "IP_POOL")
	private String ipPool;
	
	@Column(name = "HTML_CONTENT",columnDefinition = "TEXT default NULL")
	private String htmlContent;
	
	@Column(name = "PLAIN_CONTENT",columnDefinition = "TEXT default NULL")
	private String plainContent;
	

	@Column(name = "CAMPAIGN_SCHEDULE_ID")
	private String campaignScheduleId;
	

	@Column(name = "CAMPAIGN_SCHEDULE_STAUS")
	private String campaignScheduleStaus;
	

	@Column(name = "STATUS")
	private int status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY", nullable = false)
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
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

	public int getCampaignType()
	{
		return campaignType;
	}

	public void setCampaignType(int campaignType)
	{
		this.campaignType = campaignType;
	}
	
	
}
