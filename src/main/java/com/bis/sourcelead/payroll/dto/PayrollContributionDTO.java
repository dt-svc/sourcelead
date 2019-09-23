package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

import com.bis.sourcelead.dto.SearchDTO;

public class PayrollContributionDTO extends SearchDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long contributionId;
	
	private long deductionTypeId;
	
	private String deductionType;
	
	private String payrollContributionMethod;
	
	private BigDecimal contributionAmount;
	
	private String description;
	
	private int status;
	
	private String createdDate;
	
	private String createdBy;
	
	private String updatedDate;
	
	private String updatedBy;
	
	private String contributionUUID;
	
	private String contributionType;
	
	private String taxType;
	
	private String scheduleType;
	
	public long getContributionId()
	{
		return contributionId;
	}

	public void setContributionId(long contributionId)
	{
		this.contributionId = contributionId;
	}

	public long getDeductionTypeId()
	{
		return deductionTypeId;
	}

	public void setDeductionTypeId(long deductionTypeId)
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

	public String getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate)
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

	public BigDecimal getContributionAmount()
	{
		return contributionAmount;
	}

	public void setContributionAmount(BigDecimal contributionAmount)
	{
		this.contributionAmount = contributionAmount;
	}

	public String getDeductionType()
	{
		return deductionType;
	}

	public void setDeductionType(String deductionType)
	{
		this.deductionType = deductionType;
	}

	public String getContributionUUID()
	{
		return contributionUUID;
	}

	public void setContributionUUID(String contributionUUID)
	{
		this.contributionUUID = contributionUUID;
	}

	public String getContributionType()
	{
		return contributionType;
	}

	public void setContributionType(String contributionType)
	{
		this.contributionType = contributionType;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

}
