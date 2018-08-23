package com.bis.sourcelead.payroll.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

@Entity
@Table(name = "ASSOCIATE_CONTRIBUTION")
public class AssociateContribution implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "CONTRIBUTION_TYPE_ID", nullable = false)
	private PayrollContribution contributionTypeId;
	
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private Candidate candidate;
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	
	
	@Column(name = "CONTRIBUTION_METHOD")
	private String contributionMethod;
	
	@Column(name = "CONTRIBUTION_AMOUNT")
	private BigDecimal contributionAmount;
	
	
	@Column(name = "CONTRIBUTION_STATUS")
	private String contributionStatus;
	
	@Column(name = "CONTRIBUTION_START_DATE")
	private Date contributionStartDate;
	
	@Column(name = "CONTRIBUTION_END_DATE")
	private Date contributionEndDate;
	
	
	@Column(name = "STATUS")
	private Integer status;
	
	
	@Column(name = "ASSOCIATE_CONTRIBUTION_UUID")
	private String associateContributionUuid;
	
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

	public PayrollContribution getContributionTypeId()
	{
		return contributionTypeId;
	}

	public void setContributionTypeId(PayrollContribution contributionTypeId)
	{
		this.contributionTypeId = contributionTypeId;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
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

	public String getContributionMethod()
	{
		return contributionMethod;
	}

	public void setContributionMethod(String contributionMethod)
	{
		this.contributionMethod = contributionMethod;
	}

	public BigDecimal getContributionAmount()
	{
		return contributionAmount;
	}

	public void setContributionAmount(BigDecimal contributionAmount)
	{
		this.contributionAmount = contributionAmount;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}
	

	public String getAssociateContributionUuid()
	{
		return associateContributionUuid;
	}

	public void setAssociateContributionUuid(String associateContributionUuid)
	{
		this.associateContributionUuid = associateContributionUuid;
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

	public String getContributionStatus()
	{
		return contributionStatus;
	}

	public Date getContributionStartDate()
	{
		return contributionStartDate;
	}

	public Date getContributionEndDate()
	{
		return contributionEndDate;
	}

	public void setContributionStatus(String contributionStatus)
	{
		this.contributionStatus = contributionStatus;
	}

	public void setContributionStartDate(Date contributionStartDate)
	{
		this.contributionStartDate = contributionStartDate;
	}

	public void setContributionEndDate(Date contributionEndDate)
	{
		this.contributionEndDate = contributionEndDate;
	}
	
	

}
