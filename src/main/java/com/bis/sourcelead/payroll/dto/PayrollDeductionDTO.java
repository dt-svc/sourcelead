package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

import com.bis.sourcelead.dto.SearchDTO;

public class PayrollDeductionDTO  extends SearchDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	
	private long orgLocationId;
	
	private String payrollDeductionType;
	
	private String payrollDeductionMethod;
	
	private BigDecimal deductionAmount;
	
	private String description;
	
	private int status;
	
	private String createdDate;
	
	private String createdBy;
	
	private String updatedDate;
	
	private String updatedBy;
	
	private String payrollDeductionTypeName;
	
	private long lookupDataId;
	
	private String payrollScheduled;
	
	private String deductionUUID;
	
	private String taxType;
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public long getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setOrgLocationId(long orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	public String getPayrollDeductionType()
	{
		return payrollDeductionType;
	}
	public void setPayrollDeductionType(String payrollDeductionType)
	{
		this.payrollDeductionType = payrollDeductionType;
	}
	public String getPayrollDeductionMethod()
	{
		return payrollDeductionMethod;
	}
	public void setPayrollDeductionMethod(String payrollDeductionMethod)
	{
		this.payrollDeductionMethod = payrollDeductionMethod;
	}
	public BigDecimal getDeductionAmount()
	{
		return deductionAmount;
	}
	public void setDeductionAmount(BigDecimal deductionAmount)
	{
		this.deductionAmount = deductionAmount;
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
	public String getPayrollDeductionTypeName()
	{
		return payrollDeductionTypeName;
	}
	public void setPayrollDeductionTypeName(String payrollDeductionTypeName)
	{
		this.payrollDeductionTypeName = payrollDeductionTypeName;
	}
	public long getLookupDataId()
	{
		return lookupDataId;
	}
	public void setLookupDataId(long lookupDataId)
	{
		this.lookupDataId = lookupDataId;
	}
	public String getPayrollScheduled()
	{
		return payrollScheduled;
	}
	public void setPayrollScheduled(String payrollScheduled)
	{
		this.payrollScheduled = payrollScheduled;
	}
	public String getDeductionUUID()
	{
		return deductionUUID;
	}
	public void setDeductionUUID(String deductionUUID)
	{
		this.deductionUUID = deductionUUID;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	
}
