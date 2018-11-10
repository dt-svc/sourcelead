package com.bis.sourcelead.payroll.dto;

public class PayrollScheduleCalenderDTO {
	
	private String payPeriod;
	
	private String payCheckDate;
	
	private String payRunDate;
	
	private String payFrequency;

	public String getPayPeriod()
	{
		return payPeriod;
	}

	public String getPayCheckDate()
	{
		return payCheckDate;
	}

	public String getPayRunDate()
	{
		return payRunDate;
	}

	public void setPayPeriod(String payPeriod)
	{
		this.payPeriod = payPeriod;
	}

	public void setPayCheckDate(String payCheckDate)
	{
		this.payCheckDate = payCheckDate;
	}

	public void setPayRunDate(String payRunDate)
	{
		this.payRunDate = payRunDate;
	}

	public String getPayFrequency()
	{
		return payFrequency;
	}

	public void setPayFrequency(String payFrequency)
	{
		this.payFrequency = payFrequency;
	}
	
	
	
}
