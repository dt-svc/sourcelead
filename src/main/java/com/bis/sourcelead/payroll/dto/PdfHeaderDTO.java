package com.bis.sourcelead.payroll.dto;

import java.util.Date;

public class PdfHeaderDTO {
	
	private String groupName;
	
	private Date checkDate;
	
	private Date runDate;
	
	private int runNumber;
	
	private String reportType;
	
	private String orgName;
	
	private Date periodBeginDate;
	
	private Date periodEndDate;
	
	private String payPeriod;
	
	private String payrollType;

	public String getGroupName()
	{
		return groupName;
	}

	public Date getCheckDate()
	{
		return checkDate;
	}

	public Date getRunDate()
	{
		return runDate;
	}

	public int getRunNumber()
	{
		return runNumber;
	}

	public String getReportType()
	{
		return reportType;
	}

	public String getOrgName()
	{
		return orgName;
	}

	public Date getPeriodBeginDate()
	{
		return periodBeginDate;
	}

	public Date getPeriodEndDate()
	{
		return periodEndDate;
	}

	public String getPayPeriod()
	{
		return payPeriod;
	}

	public String getPayrollType()
	{
		return payrollType;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public void setCheckDate(Date checkDate)
	{
		this.checkDate = checkDate;
	}

	public void setRunDate(Date runDate)
	{
		this.runDate = runDate;
	}

	public void setRunNumber(int runNumber)
	{
		this.runNumber = runNumber;
	}

	public void setReportType(String reportType)
	{
		this.reportType = reportType;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public void setPeriodBeginDate(Date periodBeginDate)
	{
		this.periodBeginDate = periodBeginDate;
	}

	public void setPeriodEndDate(Date periodEndDate)
	{
		this.periodEndDate = periodEndDate;
	}

	public void setPayPeriod(String payPeriod)
	{
		this.payPeriod = payPeriod;
	}

	public void setPayrollType(String payrollType)
	{
		this.payrollType = payrollType;
	}
	
	
	
	
	
	
	
	
	
}
