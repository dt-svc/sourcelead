package com.bis.sourcelead.payroll.entity;

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

import com.bis.sourcelead.entity.OrgLocations;

@Entity
@Table(name = "PAYROLL_CONTRIBUTION")
public class PayrollContribution {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "DEDUCTION_TYPE_ID", nullable = false)
	private PayrollDeduction deductionTypeId;
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	
	@Column(name = "PAYROLL_CONTRIBUTION_METHOD")
	private String payrollContributionMethod;
	
	@Column(name = "CONTRIBUTION_AMOUNT")
	private BigDecimal contributionAmount;
	
	@Column(name = "DESCRIPTION", columnDefinition = "TEXT default NULL")
	private String description;
	
	@Column(name = "CONTRIBUTION_UUID")
	private String contributionUUID;
	
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

	public PayrollDeduction getDeductionTypeId()
	{
		return deductionTypeId;
	}

	public void setDeductionTypeId(PayrollDeduction deductionTypeId)
	{
		this.deductionTypeId = deductionTypeId;
	}

	public String getPayrollContributionMethod()
	{
		return payrollContributionMethod;
	}

	public void setPayrollContributionMethod(String payrollContributionMethod)
	{
		this.payrollContributionMethod = payrollContributionMethod;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
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

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public BigDecimal getContributionAmount()
	{
		return contributionAmount;
	}

	public void setContributionAmount(BigDecimal contributionAmount)
	{
		this.contributionAmount = contributionAmount;
	}

	public String getContributionUUID()
	{
		return contributionUUID;
	}

	public void setContributionUUID(String contributionUUID)
	{
		this.contributionUUID = contributionUUID;
	}

	public OrgLocations getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	
	
	
}
