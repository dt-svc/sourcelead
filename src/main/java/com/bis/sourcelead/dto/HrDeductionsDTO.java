package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.HrDeductionsDetails;

public class HrDeductionsDTO extends SearchDTO implements Serializable {
	
	private Long id;
	private OrgLocations orgLocationId;
	private String employeeId;
	private String employeeName;
	private float dedAmount;
	private Date fromDate;
	private Date toDate;
	private String batchNumber;
	private Date requestSubDate;
	private Date requestApprovedDate;
	private String repaidStatus;
	private String status;
	private String description;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
	private Set<HrDeductionsDetails> hrDeductionsDetails;
	private String fromDateStr;
	private String toDateStr;
	
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public OrgLocations getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setOrgLocationId(OrgLocations orgLocationId)
	{
		this.orgLocationId = orgLocationId;
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
	public float getDedAmount()
	{
		return dedAmount;
	}
	public void setDedAmount(float dedAmount)
	{
		this.dedAmount = dedAmount;
	}
	public Date getFromDate()
	{
		return fromDate;
	}
	public void setFromDate(Date fromDate)
	{
		this.fromDate = fromDate;
	}
	public Date getToDate()
	{
		return toDate;
	}
	public void setToDate(Date toDate)
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
	public Date getRequestSubDate()
	{
		return requestSubDate;
	}
	public void setRequestSubDate(Date requestSubDate)
	{
		this.requestSubDate = requestSubDate;
	}
	public Date getRequestApprovedDate()
	{
		return requestApprovedDate;
	}
	public void setRequestApprovedDate(Date requestApprovedDate)
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
	public Set<HrDeductionsDetails> getHrDeductionsDetails()
	{
		return hrDeductionsDetails;
	}
	public void setHrDeductionsDetails(Set<HrDeductionsDetails> hrDeductionsDetails)
	{
		this.hrDeductionsDetails = hrDeductionsDetails;
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
