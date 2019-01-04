package com.bis.sourcelead.payroll.dto;

import java.util.Date;

public class IntiateUSPayrollDTO {
	
	private String payFrequency;
	
	private Date payrollFreqStartDate;
	
	private Date payrollFreqEndDate;
	
	private Date payrollCheckDate;
	
	private Date payrollRunDate;
	
	private String groupName;

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public String getPayFrequency()
	{
		return payFrequency;
	}

	public void setPayFrequency(String payFrequency)
	{
		this.payFrequency = payFrequency;
	}

	public Date getPayrollFreqStartDate()
	{
		return payrollFreqStartDate;
	}

	public Date getPayrollFreqEndDate()
	{
		return payrollFreqEndDate;
	}

	public Date getPayrollCheckDate()
	{
		return payrollCheckDate;
	}

	public Date getPayrollRunDate()
	{
		return payrollRunDate;
	}


	public void setPayrollFreqStartDate(Date payrollFreqStartDate)
	{
		this.payrollFreqStartDate = payrollFreqStartDate;
	}

	public void setPayrollFreqEndDate(Date payrollFreqEndDate)
	{
		this.payrollFreqEndDate = payrollFreqEndDate;
	}

	public void setPayrollCheckDate(Date payrollCheckDate)
	{
		this.payrollCheckDate = payrollCheckDate;
	}

	public void setPayrollRunDate(Date payrollRunDate)
	{
		this.payrollRunDate = payrollRunDate;
	}
	
	
}
