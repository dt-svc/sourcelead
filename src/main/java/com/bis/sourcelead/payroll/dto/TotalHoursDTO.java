package com.bis.sourcelead.payroll.dto;

import java.math.BigInteger;
import java.util.Date;

public class TotalHoursDTO {
	
	private BigInteger candidateID;
    
	private BigInteger projectMemId;
	
	private BigInteger projectId;
    
	private double payRate;
    
	private String payRateCurrency;
    
	private double currentBillRate;
    
	private String billRateCurrency;
    
	private String payRateType;
	
    private BigInteger submissinId;
    
	private Date startDate;
    
	private Date endDate;
	
	private Date timesheetDate;
    
	private String billableHours;
    
	private String totalHours;
    
	private String estimatedHours;
    
	private String nonBillableHours;
	
	
	public BigInteger getProjectId()
	{
		return projectId;
	}

	public void setProjectId(BigInteger projectId)
	{
		this.projectId = projectId;
	}

	public double getPayRate()
	{
		return payRate;
	}

	public String getPayRateCurrency()
	{
		return payRateCurrency;
	}

	public double getCurrentBillRate()
	{
		return currentBillRate;
	}

	public String getBillRateCurrency()
	{
		return billRateCurrency;
	}

	public String getPayRateType()
	{
		return payRateType;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public String getBillableHours()
	{
		return billableHours;
	}

	public String getTotalHours()
	{
		return totalHours;
	}

	public String getEstimatedHours()
	{
		return estimatedHours;
	}

	public String getNonBillableHours()
	{
		return nonBillableHours;
	}


	public void setPayRate(double payRate)
	{
		this.payRate = payRate;
	}

	public void setPayRateCurrency(String payRateCurrency)
	{
		this.payRateCurrency = payRateCurrency;
	}

	public void setCurrentBillRate(double currentBillRate)
	{
		this.currentBillRate = currentBillRate;
	}

	public void setBillRateCurrency(String billRateCurrency)
	{
		this.billRateCurrency = billRateCurrency;
	}

	public void setPayRateType(String payRateType)
	{
		this.payRateType = payRateType;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public void setBillableHours(String billableHours)
	{
		this.billableHours = billableHours;
	}

	public void setTotalHours(String totalHours)
	{
		this.totalHours = totalHours;
	}

	public void setEstimatedHours(String estimatedHours)
	{
		this.estimatedHours = estimatedHours;
	}

	public void setNonBillableHours(String nonBillableHours)
	{
		this.nonBillableHours = nonBillableHours;
	}


	public BigInteger getCandidateID()
	{
		return candidateID;
	}


	public void setCandidateID(BigInteger candidateID)
	{
		this.candidateID = candidateID;
	}

	public BigInteger getProjectMemId()
	{
		return projectMemId;
	}

	public void setProjectMemId(BigInteger projectMemId)
	{
		this.projectMemId = projectMemId;
	}

	public BigInteger getSubmissinId()
	{
		return submissinId;
	}

	public void setSubmissinId(BigInteger submissinId)
	{
		this.submissinId = submissinId;
	}

	public Date getTimesheetDate()
	{
		return timesheetDate;
	}

	public void setTimesheetDate(Date timesheetDate)
	{
		this.timesheetDate = timesheetDate;
	}


	
	
	
}
