package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.UUID;

import com.bis.sourcelead.annotations.Required;

public class HolidayDTO implements Serializable {
private static final long serialVersionUID = 1L;

	private long id;
	
	private String orgLocationId;
	
	private String userId;
	
	@Required(desc = "Holiday Name")
	private String holidayName;  
	
	@Required(desc = "From Date")
	private String fromDate;
	
	@Required(desc = "To Date")
	private String toDate;
	
	private String fromHalfDayCheckBox;
	
	private String fromHalfDayAmPm;
	
	private String toHalfDayCheckBox;
	
	private String toHalfDayAmPm;
	
	private String description;
	
	private String holidayStatus;
	
	private String status;
	
	private String holidayId;
	
	private String dayOfTheWeek;
	
	private String fromDateStr;
	
	private String toDateStr;
	
	private String isCurrent;
	
	private String isFuture;
	
	private String isPast;
	
	private String holidayBillableType;
	
	private String holidayUUID;
	
	private String shift;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getHolidayName()
	{
		return holidayName;
	}

	public void setHolidayName(String holidayName)
	{
		this.holidayName = holidayName;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public String getFromHalfDayCheckBox()
	{
		return fromHalfDayCheckBox;
	}

	public void setFromHalfDayCheckBox(String fromHalfDayCheckBox)
	{
		this.fromHalfDayCheckBox = fromHalfDayCheckBox;
	}

	public String getFromHalfDayAmPm()
	{
		return fromHalfDayAmPm;
	}

	public void setFromHalfDayAmPm(String fromHalfDayAmPm)
	{
		this.fromHalfDayAmPm = fromHalfDayAmPm;
	}

	public String getToHalfDayCheckBox()
	{
		return toHalfDayCheckBox;
	}

	public void setToHalfDayCheckBox(String toHalfDayCheckBox)
	{
		this.toHalfDayCheckBox = toHalfDayCheckBox;
	}

	public String getToHalfDayAmPm()
	{
		return toHalfDayAmPm;
	}

	public void setToHalfDayAmPm(String toHalfDayAmPm)
	{
		this.toHalfDayAmPm = toHalfDayAmPm;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getHolidayStatus()
	{
		return holidayStatus;
	}

	public void setHolidayStatus(String holidayStatus)
	{
		this.holidayStatus = holidayStatus;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getHolidayId()
	{
		return holidayId;
	}

	public void setHolidayId(String holidayId)
	{
		this.holidayId = holidayId;
	}

	public String getDayOfTheWeek()
	{
		return dayOfTheWeek;
	}

	public void setDayOfTheWeek(String dayOfTheWeek)
	{
		this.dayOfTheWeek = dayOfTheWeek;
	}

	public String getFromDateStr()
	{
		return fromDateStr;
	}

	public void setFromDateStr(String fromDateStr)
	{
		this.fromDateStr = fromDateStr;
	}

	public String getToDateStr()
	{
		return toDateStr;
	}

	public void setToDateStr(String toDateStr)
	{
		this.toDateStr = toDateStr;
	}

	public String getIsCurrent()
	{
		return isCurrent;
	}

	public void setIsCurrent(String isCurrent)
	{
		this.isCurrent = isCurrent;
	}

	public String getIsFuture()
	{
		return isFuture;
	}

	public void setIsFuture(String isFuture)
	{
		this.isFuture = isFuture;
	}

	public String getIsPast()
	{
		return isPast;
	}

	public void setIsPast(String isPast)
	{
		this.isPast = isPast;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public String getHolidayUUID()
	{
		return holidayUUID;
	}

	public void setHolidayUUID(String holidayUUID)
	{
		this.holidayUUID = holidayUUID;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getHolidayBillableType()
	{
		return holidayBillableType;
	}

	public void setHolidayBillableType(String holidayBillableType)
	{
		this.holidayBillableType = holidayBillableType;
	}
	
}
