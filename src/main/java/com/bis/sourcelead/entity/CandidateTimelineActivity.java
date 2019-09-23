package com.bis.sourcelead.entity;

import java.io.Serializable;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "CANDIDATE_TIMELINE_ACTIVITY")
public class CandidateTimelineActivity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE", nullable = false)
	private Candidate candidate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	@Column(name = "ACTION_BY")
	private String actionBy;
	@Column(name = "ACTION_ON")
	private String actionOn;
	@Column(name = "ACTION_DESC", columnDefinition = "TEXT default NULL")
	private String actionDesc;
	@Column(name = "ACTION_SUMMARY", columnDefinition = "TEXT default NULL")
	private String actionSummary;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_USER")
	private User createdUser;
	@Column(name = "ACTION_ICON")
	private String actionIcon;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	@Transient
	@JsonProperty
	private String actionDateStr;
	@Transient
	@JsonProperty
	private String actionTimeStr;
	@Transient
	@JsonProperty
	private String actionIconClass;
	@Transient
	@JsonProperty
	private Long createdUserId;
	
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
	
	public String getActionIcon()
	{
		return actionIcon;
	}
	
	public void setActionIcon(String actionIcon)
	{
		this.actionIcon = actionIcon;
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
	
	public String getActionDateStr()
	{
		return actionDateStr;
	}
	
	public void setActionDateStr(String actionDateStr)
	{
		this.actionDateStr = actionDateStr;
	}
	
	public String getActionTimeStr()
	{
		return actionTimeStr;
	}
	
	public void setActionTimeStr(String actionTimeStr)
	{
		this.actionTimeStr = actionTimeStr;
	}
	
	public String getActionIconClass()
	{
		return actionIconClass;
	}
	
	public void setActionIconClass(String actionIconClass)
	{
		this.actionIconClass = actionIconClass;
	}
	
	public Long getCreatedUserId()
	{
		return createdUserId;
	}
	
	public void setCreatedUserId(Long createdUserId)
	{
		this.createdUserId = createdUserId;
	}
}
