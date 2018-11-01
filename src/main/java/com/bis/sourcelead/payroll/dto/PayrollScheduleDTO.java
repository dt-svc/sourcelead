package com.bis.sourcelead.payroll.dto;

import com.bis.sourcelead.annotations.Required;

public class PayrollScheduleDTO {
	
	private long id;
	
	private String orgLocationId;
	
	@Required(desc = "Pay Frequency")
	private String payFrequency;
	
	@Required(desc = "Pay PeriodStartDate")
	private String payPeriodStartDate;
	
	@Required(desc = "Pay CheckDate")
	private String payCheckDate;
	
	@Required(desc = "Run PayrollDate")
	private String runPayDate;
	
	private String nextPayMonth;
	
	private String notifyMember;
	
	private int status;
	
	public String getOrgLocationId() {
		return orgLocationId;
	}
	
	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getPayFrequency()
	{
		return payFrequency;
	}

	public String getPayPeriodStartDate()
	{
		return payPeriodStartDate;
	}

	public String getPayCheckDate()
	{
		return payCheckDate;
	}


	public String getNextPayMonth()
	{
		return nextPayMonth;
	}

	public int getStatus()
	{
		return status;
	}

	public void setPayFrequency(String payFrequency)
	{
		this.payFrequency = payFrequency;
	}

	public void setPayPeriodStartDate(String payPeriodStartDate)
	{
		this.payPeriodStartDate = payPeriodStartDate;
	}

	public void setPayCheckDate(String payCheckDate)
	{
		this.payCheckDate = payCheckDate;
	}


	public void setNextPayMonth(String nextPayMonth)
	{
		this.nextPayMonth = nextPayMonth;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getNotifyMember()
	{
		return notifyMember;
	}

	public void setNotifyMember(String notifyMember)
	{
		this.notifyMember = notifyMember;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getRunPayDate()
	{
		return runPayDate;
	}

	public void setRunPayDate(String runPayDate)
	{
		this.runPayDate = runPayDate;
	}
	
	
}
