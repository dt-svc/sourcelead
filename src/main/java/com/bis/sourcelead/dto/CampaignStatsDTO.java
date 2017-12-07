package com.bis.sourcelead.dto;

import com.bis.sourcelead.entity.OrgLocations;

public class CampaignStatsDTO {
	
	private Long id;
	private Long orgLocation;
	private String categoryName;
	private String delivered;
	private String uniqueOpens;
	private String uniqueClicks;
	private String unsubscribes;
	private String status;
	private String opens;
	
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getOrgLocation()
	{
		return orgLocation;
	}
	public void setOrgLocation(Long orgLocation)
	{
		this.orgLocation = orgLocation;
	}
	public String getCategoryName()
	{
		return categoryName;
	}
	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}
	public String getDelivered()
	{
		return delivered;
	}
	public void setDelivered(String delivered)
	{
		this.delivered = delivered;
	}
	public String getUniqueOpens()
	{
		return uniqueOpens;
	}
	public void setUniqueOpens(String uniqueOpens)
	{
		this.uniqueOpens = uniqueOpens;
	}
	public String getUniqueClicks()
	{
		return uniqueClicks;
	}
	public void setUniqueClicks(String uniqueClicks)
	{
		this.uniqueClicks = uniqueClicks;
	}
	public String getUnsubscribes()
	{
		return unsubscribes;
	}
	public void setUnsubscribes(String unsubscribes)
	{
		this.unsubscribes = unsubscribes;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getOpens()
	{
		return opens;
	}
	public void setOpens(String opens)
	{
		this.opens = opens;
	}
	
}
