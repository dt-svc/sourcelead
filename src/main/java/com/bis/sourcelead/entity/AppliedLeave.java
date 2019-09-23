package com.bis.sourcelead.entity;

import java.math.BigInteger;
import java.util.Date;


public class AppliedLeave {
	private BigInteger id;
	
	private Date appliedDate;
	
	private BigInteger orgLocationId;
	
	private BigInteger leaveTypeId;
	
	private String leaveStatus;
	
	private String fromhalfDay;
	
	private String toHalfDay;
	
	private String includeHoliday;
	
	private String includeInPayroll;
	
	private String includeWeekOff;
	
	private String duration;
	
    private Date fromDate;
    
    private Date toDate;
    
    private String formDateFromTime;
    
    private String fromDateToTime;
    
    private String toDateFromTime;
    
    private String toDateToTime;
	
	public BigInteger getId()
	{
		return id;
	}
	
	public Date getAppliedDate()
	{
		return appliedDate;
	}
	
	public BigInteger getOrgLocationId()
	{
		return orgLocationId;
	}
	
	public BigInteger getLeaveTypeId()
	{
		return leaveTypeId;
	}
	
	public String getLeaveStatus()
	{
		return leaveStatus;
	}
	
	public String getFromhalfDay()
	{
		return fromhalfDay;
	}
	
	public String getToHalfDay()
	{
		return toHalfDay;
	}
	
	public String getIncludeHoliday()
	{
		return includeHoliday;
	}
	
	public String getIncludeInPayroll()
	{
		return includeInPayroll;
	}
	
	public String getIncludeWeekOff()
	{
		return includeWeekOff;
	}
	
	public void setId(BigInteger id)
	{
		this.id = id;
	}
	
	public void setAppliedDate(Date appliedDate)
	{
		this.appliedDate = appliedDate;
	}
	
	public void setOrgLocationId(BigInteger orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	
	public void setLeaveTypeId(BigInteger leaveTypeId)
	{
		this.leaveTypeId = leaveTypeId;
	}
	
	public void setLeaveStatus(String leaveStatus)
	{
		this.leaveStatus = leaveStatus;
	}
	
	public void setFromhalfDay(String fromhalfDay)
	{
		this.fromhalfDay = fromhalfDay;
	}
	
	public void setToHalfDay(String toHalfDay)
	{
		this.toHalfDay = toHalfDay;
	}
	
	public void setIncludeHoliday(String includeHoliday)
	{
		this.includeHoliday = includeHoliday;
	}
	
	public void setIncludeInPayroll(String includeInPayroll)
	{
		this.includeInPayroll = includeInPayroll;
	}
	
	public void setIncludeWeekOff(String includeWeekOff)
	{
		this.includeWeekOff = includeWeekOff;
	}

	public String getDuration()
	{
		return duration;
	}

	public void setDuration(String duration)
	{
		this.duration = duration;
	}

	public Date getFromDate()
	{
		return fromDate;
	}

	public Date getToDate()
	{
		return toDate;
	}

	public String getFormDateFromTime()
	{
		return formDateFromTime;
	}

	public String getFromDateToTime()
	{
		return fromDateToTime;
	}

	public String getToDateFromTime()
	{
		return toDateFromTime;
	}

	public String getToDateToTime()
	{
		return toDateToTime;
	}

	public void setFromDate(Date fromDate)
	{
		this.fromDate = fromDate;
	}

	public void setToDate(Date toDate)
	{
		this.toDate = toDate;
	}

	public void setFormDateFromTime(String formDateFromTime)
	{
		this.formDateFromTime = formDateFromTime;
	}

	public void setFromDateToTime(String fromDateToTime)
	{
		this.fromDateToTime = fromDateToTime;
	}

	public void setToDateFromTime(String toDateFromTime)
	{
		this.toDateFromTime = toDateFromTime;
	}

	public void setToDateToTime(String toDateToTime)
	{
		this.toDateToTime = toDateToTime;
	}
	
	
}
