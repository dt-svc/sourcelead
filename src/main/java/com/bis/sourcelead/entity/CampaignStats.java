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
@Table(name = "CAMPAIGN_STATS")
public class CampaignStats {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGLOCATION_ID", nullable = false)
	private OrgLocations orgLocation;
	
	@Column(name = "CAMPAIGN_DATE")
	private Date campaignDate;
	
	@Column(name = "CATEGORY_NAME")
	private String categoryName;
	
	@Column(name = "CAMPAIGN_ID")
	private String campaignId;
	
	
	@Column(name = "BLOCKS")
	private String blocks;
	
	@Column(name = "BOUNCE_DROPS")
	private String bounceDrops;
	
	@Column(name = "BOUNCES")
	private String bounces;
	

	@Column(name = "CLICKS")
	private String clicks;
	
	@Column(name = "DEFERRED")
	private String deferred;
	
	@Column(name = "DELIVERED")
	private String delivered;
	
	@Column(name = "INVALID_EMAILS")
	private String invalidEmails;
	

	@Column(name = "OPENS")
	private String opens;
	
	@Column(name = "PROCESSED")
	private String processed;
	
	@Column(name = "REQUEST")
	private String request;
	
	@Column(name = "SPAM_REPORT_DROPS")
	private String spamReportDrops;
	
	@Column(name = "SPAM_REPORTS")
	private String spamReports;
	
	@Column(name = "UNIQUE_CLICKS")
	private String uniqueClicks;
	
	@Column(name = "UNIQUE_OPENS")
	private String uniqueOpens;
	
	@Column(name = "UNSUBSCRIBE_DROPS")
	private String unsubscribeDrops;
	
	@Column(name = "UNSUBSCRIBES")
	private String unsubscribes;
	
	@Column(name = "STATUS")
	private int status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
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

	public Date getCampaignDate()
	{
		return campaignDate;
	}

	public void setCampaignDate(Date campaignDate)
	{
		this.campaignDate = campaignDate;
	}

	public String getCategoryName()
	{
		return categoryName;
	}

	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}

	public String getBlocks()
	{
		return blocks;
	}

	public void setBlocks(String blocks)
	{
		this.blocks = blocks;
	}

	public String getBounceDrops()
	{
		return bounceDrops;
	}

	public void setBounceDrops(String bounceDrops)
	{
		this.bounceDrops = bounceDrops;
	}

	public String getBounces()
	{
		return bounces;
	}

	public void setBounces(String bounces)
	{
		this.bounces = bounces;
	}

	public String getClicks()
	{
		return clicks;
	}

	public void setClicks(String clicks)
	{
		this.clicks = clicks;
	}

	public String getDeferred()
	{
		return deferred;
	}

	public void setDeferred(String deferred)
	{
		this.deferred = deferred;
	}

	public String getDelivered()
	{
		return delivered;
	}

	public void setDelivered(String delivered)
	{
		this.delivered = delivered;
	}

	public String getInvalidEmails()
	{
		return invalidEmails;
	}

	public void setInvalidEmails(String invalidEmails)
	{
		this.invalidEmails = invalidEmails;
	}

	public String getOpens()
	{
		return opens;
	}

	public void setOpens(String opens)
	{
		this.opens = opens;
	}

	public String getProcessed()
	{
		return processed;
	}

	public void setProcessed(String processed)
	{
		this.processed = processed;
	}

	public String getRequest()
	{
		return request;
	}

	public void setRequest(String request)
	{
		this.request = request;
	}

	public String getSpamReportDrops()
	{
		return spamReportDrops;
	}

	public void setSpamReportDrops(String spamReportDrops)
	{
		this.spamReportDrops = spamReportDrops;
	}

	public String getSpamReports()
	{
		return spamReports;
	}

	public void setSpamReports(String spamReports)
	{
		this.spamReports = spamReports;
	}

	public String getUniqueClicks()
	{
		return uniqueClicks;
	}

	public void setUniqueClicks(String uniqueClicks)
	{
		this.uniqueClicks = uniqueClicks;
	}

	public String getUniqueOpens()
	{
		return uniqueOpens;
	}

	public void setUniqueOpens(String uniqueOpens)
	{
		this.uniqueOpens = uniqueOpens;
	}

	public String getUnsubscribeDrops()
	{
		return unsubscribeDrops;
	}

	public void setUnsubscribeDrops(String unsubscribeDrops)
	{
		this.unsubscribeDrops = unsubscribeDrops;
	}

	public String getUnsubscribes()
	{
		return unsubscribes;
	}

	public void setUnsubscribes(String unsubscribes)
	{
		this.unsubscribes = unsubscribes;
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

	public String getCampaignId()
	{
		return campaignId;
	}

	public void setCampaignId(String campaignId)
	{
		this.campaignId = campaignId;
	}
	
}
