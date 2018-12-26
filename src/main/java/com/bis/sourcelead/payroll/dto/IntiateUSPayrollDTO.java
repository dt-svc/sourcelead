package com.bis.sourcelead.payroll.dto;

import java.util.Date;

public class IntiateUSPayrollDTO {
	
	private String payFreqency;
	
	private Date payrollFreqStartDate;
	
	private Date payrollFreqEndDate;
	
	private Date payrollCheckDate;
	
	private Date payrollRunDate;

	public String getPayFreqency()
	{
		return payFreqency;
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

	public void setPayFreqency(String payFreqency)
	{
		this.payFreqency = payFreqency;
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
