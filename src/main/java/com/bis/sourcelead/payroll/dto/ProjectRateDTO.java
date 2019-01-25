package com.bis.sourcelead.payroll.dto;

import java.math.BigInteger;
import java.util.Date;

public class ProjectRateDTO {
	
	private BigInteger projectMemId;
	
	private Double billRate;
	
	private String billRateType;
	
	private Date billRateStartDate;
	
	private Date billRateEndDate;
	
	private Double payRate;
	
	private String payRateType;
	
	private Date payRateStartDate;
	
	private Date payRateEndDate;
	
	private BigInteger candidateId;
	
	private BigInteger projectId;
	
	private BigInteger projectClientId;
	
	private Date projectAssignedDate;
	
	private Date projectEndDate;
	
	private Double actualPayRate;
	
	private String actualPayRateType;

	public BigInteger getProjectMemId()
	{
		return projectMemId;
	}

	public Double getBillRate()
	{
		return billRate;
	}

	public String getBillRateType()
	{
		return billRateType;
	}

	public Date getBillRateStartDate()
	{
		return billRateStartDate;
	}

	public Date getBillRateEndDate()
	{
		return billRateEndDate;
	}

	public Double getPayRate()
	{
		return payRate;
	}

	public String getPayRateType()
	{
		return payRateType;
	}

	public Date getPayRateStartDate()
	{
		return payRateStartDate;
	}

	public Date getPayRateEndDate()
	{
		return payRateEndDate;
	}

	public BigInteger getCandidateId()
	{
		return candidateId;
	}

	public BigInteger getProjectId()
	{
		return projectId;
	}

	public BigInteger getProjectClientId()
	{
		return projectClientId;
	}

	public Date getProjectAssignedDate()
	{
		return projectAssignedDate;
	}

	public Date getProjectEndDate()
	{
		return projectEndDate;
	}

	public Double getActualPayRate()
	{
		return actualPayRate;
	}

	public String getActualPayRateType()
	{
		return actualPayRateType;
	}

	public void setProjectMemId(BigInteger projectMemId)
	{
		this.projectMemId = projectMemId;
	}

	public void setBillRate(Double billRate)
	{
		this.billRate = billRate;
	}

	public void setBillRateType(String billRateType)
	{
		this.billRateType = billRateType;
	}

	public void setBillRateStartDate(Date billRateStartDate)
	{
		this.billRateStartDate = billRateStartDate;
	}

	public void setBillRateEndDate(Date billRateEndDate)
	{
		this.billRateEndDate = billRateEndDate;
	}

	public void setPayRate(Double payRate)
	{
		this.payRate = payRate;
	}

	public void setPayRateType(String payRateType)
	{
		this.payRateType = payRateType;
	}

	public void setPayRateStartDate(Date payRateStartDate)
	{
		this.payRateStartDate = payRateStartDate;
	}

	public void setPayRateEndDate(Date payRateEndDate)
	{
		this.payRateEndDate = payRateEndDate;
	}

	public void setCandidateId(BigInteger candidateId)
	{
		this.candidateId = candidateId;
	}

	public void setProjectId(BigInteger projectId)
	{
		this.projectId = projectId;
	}

	public void setProjectClientId(BigInteger projectClientId)
	{
		this.projectClientId = projectClientId;
	}

	public void setProjectAssignedDate(Date projectAssignedDate)
	{
		this.projectAssignedDate = projectAssignedDate;
	}

	public void setProjectEndDate(Date projectEndDate)
	{
		this.projectEndDate = projectEndDate;
	}

	public void setActualPayRate(Double actualPayRate)
	{
		this.actualPayRate = actualPayRate;
	}

	public void setActualPayRateType(String actualPayRateType)
	{
		this.actualPayRateType = actualPayRateType;
	}
	
	
}
