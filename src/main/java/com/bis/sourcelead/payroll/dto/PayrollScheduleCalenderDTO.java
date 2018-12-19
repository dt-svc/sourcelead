package com.bis.sourcelead.payroll.dto;

public class PayrollScheduleCalenderDTO {
	
	private String payPeriod;
	
	private String payCheckDate;
	
	private String payRunDate;
	
	private String payFrequency;
	
	private String startDate;
	
	private String endDate;
	
	private String dateFormat;
	
	private int month;
	
	private String payCheckDateFormat;
	
	private String payRunDateFormat;

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

	public String getStartDate()
	{
		return startDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public String getDateFormat()
	{
		return dateFormat;
	}

	public void setDateFormat(String dateFormat)
	{
		this.dateFormat = dateFormat;
	}

	public int getMonth()
	{
		return month;
	}

	public void setMonth(int month)
	{
		this.month = month;
	}

	public String getPayCheckDateFormat()
	{
		return payCheckDateFormat;
	}

	public String getPayRunDateFormat()
	{
		return payRunDateFormat;
	}

	public void setPayCheckDateFormat(String payCheckDateFormat)
	{
		this.payCheckDateFormat = payCheckDateFormat;
	}

	public void setPayRunDateFormat(String payRunDateFormat)
	{
		this.payRunDateFormat = payRunDateFormat;
	}
	
	
}
