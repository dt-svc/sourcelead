package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.AccountDeductionsDetails;

public class AccountDeductionsDTO extends SearchDTO implements Serializable {
	
	
	private String id;
	private String orgLocationId;
	private String deductionId;
	private String employeeId;
	private String employeeName;
	private String dedAmount;
	private String fromDate;
	private String toDate;
	private String batchNumber;
	private String requestSubDate;
	private String requestApprovedDate;
	private String repaidStatus;
	private String status;
	private String description;
	private String createdDate;
	private String createdBy;
	private String updatedDate;
	private String updatedBy;
	private Set<AccountDeductionsDetails> accountDeductionsDetails;
	private String fromDateStr;
	private String toDateStr;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setOrgLocationId(String orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	public String getDeductionId()
	{
		return deductionId;
	}
	public void setDeductionId(String deductionId)
	{
		this.deductionId = deductionId;
	}
	public String getEmployeeId()
	{
		return employeeId;
	}
	public void setEmployeeId(String employeeId)
	{
		this.employeeId = employeeId;
	}
	public String getEmployeeName()
	{
		return employeeName;
	}
	public void setEmployeeName(String employeeName)
	{
		this.employeeName = employeeName;
	}
	public String getDedAmount()
	{
		return dedAmount;
	}
	public void setDedAmount(String dedAmount)
	{
		this.dedAmount = dedAmount;
	}
	public String getFromDate()
	{
		return fromDate;
	}
	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}
	public String getToDate()
	{
		return toDate;
	}
	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}
	public String getBatchNumber()
	{
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber)
	{
		this.batchNumber = batchNumber;
	}
	public String getRequestSubDate()
	{
		return requestSubDate;
	}
	public void setRequestSubDate(String requestSubDate)
	{
		this.requestSubDate = requestSubDate;
	}
	public String getRequestApprovedDate()
	{
		return requestApprovedDate;
	}
	public void setRequestApprovedDate(String requestApprovedDate)
	{
		this.requestApprovedDate = requestApprovedDate;
	}
	public String getRepaidStatus()
	{
		return repaidStatus;
	}
	public void setRepaidStatus(String repaidStatus)
	{
		this.repaidStatus = repaidStatus;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
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
	public Set<AccountDeductionsDetails> getAccountDeductionsDetails()
	{
		return accountDeductionsDetails;
	}
	public void setAccountDeductionsDetails(Set<AccountDeductionsDetails> accountDeductionsDetails)
	{
		this.accountDeductionsDetails = accountDeductionsDetails;
	}
	public String getFromDateStr()
	{
		return fromDateStr;
	}
	public void setFromDateStr(String fromDateStr)
	{
		this.fromDateStr = fromDateStr;
	}
	public String getToDateStr()
	{
		return toDateStr;
	}
	public void setToDateStr(String toDateStr)
	{
		this.toDateStr = toDateStr;
	}
	
	
	
	
}
