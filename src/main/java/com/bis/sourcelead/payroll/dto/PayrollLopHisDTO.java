package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PayrollLopHisDTO {
	
	private String id;
	
	private String payrollId;
	
	private String leaveTypeId;
	
	private String leaveTypeName;
	
	private float totalBalanceLeaves;
	
	private float totalAppliedLeaves;
	
	private float lopLeaves;
	
	private BigDecimal lopAmount;
	
	private String payrollStartDate;
	
	private String payrollEndDate;
	
	private String payrollLopHisUUID;
	
	private String lopExempt;
	
	private Integer status;
	
	private Date createdDate;
	
	private Long createdBy;
	
	private Date updatedDate;
	
	private Long updatedBy;
	
	private BigDecimal totLopAmount;
	

	public String getId()
	{
		return id;
	}

	public String getPayrollId()
	{
		return payrollId;
	}

	public String getLeaveTypeId()
	{
		return leaveTypeId;
	}

	public String getLeaveTypeName()
	{
		return leaveTypeName;
	}

	public float getTotalBalanceLeaves()
	{
		return totalBalanceLeaves;
	}

	public float getTotalAppliedLeaves()
	{
		return totalAppliedLeaves;
	}

	public float getLopLeaves()
	{
		return lopLeaves;
	}

	public BigDecimal getLopAmount()
	{
		return lopAmount;
	}

	public String getPayrollStartDate()
	{
		return payrollStartDate;
	}

	public String getPayrollEndDate()
	{
		return payrollEndDate;
	}

	public String getPayrollLopHisUUID()
	{
		return payrollLopHisUUID;
	}

	public String getLopExempt()
	{
		return lopExempt;
	}

	public Integer getStatus()
	{
		return status;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public Long getCreatedBy()
	{
		return createdBy;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public Long getUpdatedBy()
	{
		return updatedBy;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setPayrollId(String payrollId)
	{
		this.payrollId = payrollId;
	}

	public void setLeaveTypeId(String leaveTypeId)
	{
		this.leaveTypeId = leaveTypeId;
	}

	public void setLeaveTypeName(String leaveTypeName)
	{
		this.leaveTypeName = leaveTypeName;
	}

	public void setTotalBalanceLeaves(float totalBalanceLeaves)
	{
		this.totalBalanceLeaves = totalBalanceLeaves;
	}

	public void setTotalAppliedLeaves(float totalAppliedLeaves)
	{
		this.totalAppliedLeaves = totalAppliedLeaves;
	}

	public void setLopLeaves(float lopLeaves)
	{
		this.lopLeaves = lopLeaves;
	}

	public void setLopAmount(BigDecimal lopAmount)
	{
		this.lopAmount = lopAmount;
	}

	public void setPayrollStartDate(String payrollStartDate)
	{
		this.payrollStartDate = payrollStartDate;
	}

	public void setPayrollEndDate(String payrollEndDate)
	{
		this.payrollEndDate = payrollEndDate;
	}

	public void setPayrollLopHisUUID(String payrollLopHisUUID)
	{
		this.payrollLopHisUUID = payrollLopHisUUID;
	}

	public void setLopExempt(String lopExempt)
	{
		this.lopExempt = lopExempt;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public void setCreatedBy(Long createdBy)
	{
		this.createdBy = createdBy;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public void setUpdatedBy(Long updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public BigDecimal getTotLopAmount()
	{
		return totLopAmount;
	}

	public void setTotLopAmount(BigDecimal totLopAmount)
	{
		this.totLopAmount = totLopAmount;
	}

	
	
}
