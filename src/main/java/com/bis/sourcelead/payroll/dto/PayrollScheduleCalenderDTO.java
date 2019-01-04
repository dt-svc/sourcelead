package com.bis.sourcelead.payroll.dto;

import java.util.Date;

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
	
	private Date payPeriodStartDate;
	
	private Date payPeriodEndDate;
	
	private Date checkDate;
	
	private Date runDate;
	

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

	public Date getPayPeriodStartDate()
	{
		return payPeriodStartDate;
	}

	public Date getPayPeriodEndDate()
	{
		return payPeriodEndDate;
	}

	public Date getCheckDate()
	{
		return checkDate;
	}

	public Date getRunDate()
	{
		return runDate;
	}

	public void setPayPeriodStartDate(Date payPeriodStartDate)
	{
		this.payPeriodStartDate = payPeriodStartDate;
	}

	public void setPayPeriodEndDate(Date payPeriodEndDate)
	{
		this.payPeriodEndDate = payPeriodEndDate;
	}

	public void setCheckDate(Date checkDate)
	{
		this.checkDate = checkDate;
	}

	public void setRunDate(Date runDate)
	{
		this.runDate = runDate;
	}
	
	
}
