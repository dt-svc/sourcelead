package com.bis.sourcelead.dto;

public class ShiftDTO {
	
	
	private String userId;
	private String orgLocationId;
	private String shiftName;
	private String timeIn;
	private String timeOut;
	private String shiftDescription;
	private String shiftId;
	
	
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setOrgLocationId(String orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	public String getShiftName()
	{
		return shiftName;
	}
	public void setShiftName(String shiftName)
	{
		this.shiftName = shiftName;
	}
	public String getTimeIn()
	{
		return timeIn;
	}
	public void setTimeIn(String timeIn)
	{
		this.timeIn = timeIn;
	}
	public String getTimeOut()
	{
		return timeOut;
	}
	public void setTimeOut(String timeOut)
	{
		this.timeOut = timeOut;
	}
	public String getShiftDescription()
	{
		return shiftDescription;
	}
	public void setShiftDescription(String shiftDescription)
	{
		this.shiftDescription = shiftDescription;
	}
	public String getShiftId()
	{
		return shiftId;
	}
	public void setShiftId(String shiftId)
	{
		this.shiftId = shiftId;
	}
	
	
}
