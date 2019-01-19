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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "SAVED_JOBS")
public class SavedJobs implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")        
    private User userId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REQUIREMENT_ID")        
    private Requirement requirementId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REFER_ID")        
    private RequirementRefer referId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORGLOCATION_ID")        
    private OrgLocations orgLocationId;

	@Column(name = "SAVED_JOB_UUID")
	private String savedJobUUID;
	
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
	
	

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public User getUserId()
	{
		return userId;
	}

	public void setUserId(User userId)
	{
		this.userId = userId;
	}

	public Requirement getRequirementId()
	{
		return requirementId;
	}

	public void setRequirementId(Requirement requirementId)
	{
		this.requirementId = requirementId;
	}

	public RequirementRefer getReferId()
	{
		return referId;
	}

	public void setReferId(RequirementRefer referId)
	{
		this.referId = referId;
	}

	public OrgLocations getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public String getSavedJobUUID()
	{
		return savedJobUUID;
	}

	public void setSavedJobUUID(String savedJobUUID)
	{
		this.savedJobUUID = savedJobUUID;
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
