package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public class CandidateTimelineActivityDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Candidate candidate;
	private OrgLocations orgLocationId;
	private String actionBy;
	private String actionOn;
	private String actionDesc;
	private String actionSummary;
	private User createdUser;
	private Integer status;
	private Date createdDate;
	private String createdBy;
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
	
	public Candidate getCandidate()
	{
		return candidate;
	}
	
	public void setCandidate(Candidate candidate)
	{
		this.candidate = candidate;
	}
	
	public OrgLocations getOrgLocationId()
	{
		return orgLocationId;
	}
	
	public void setOrgLocationId(OrgLocations orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	
	public String getActionBy()
	{
		return actionBy;
	}
	
	public void setActionBy(String actionBy)
	{
		this.actionBy = actionBy;
	}
	
	public String getActionOn()
	{
		return actionOn;
	}
	
	public void setActionOn(String actionOn)
	{
		this.actionOn = actionOn;
	}
	
	public String getActionDesc()
	{
		return actionDesc;
	}
	
	public void setActionDesc(String actionDesc)
	{
		this.actionDesc = actionDesc;
	}
	
	public String getActionSummary()
	{
		return actionSummary;
	}
	
	public void setActionSummary(String actionSummary)
	{
		this.actionSummary = actionSummary;
	}
	
	public User getCreatedUser()
	{
		return createdUser;
	}
	
	public void setCreatedUser(User createdUser)
	{
		this.createdUser = createdUser;
	}
	
	public Integer getStatus()
	{
		return status;
	}
	
	public void setStatus(Integer status)
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
}
