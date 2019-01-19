package com.bis.sourcelead.dto;

public class LeaveTypeEditDTO {
	
	 private int status;
	 private float totalLeaves;
	 private long locationId;
	 private long userId;
	 private long leaveTypeId;
	 private String leavetypeRoles;
	 private String  leavetypeDepartment;
	 private String  leavetypeEmpType;
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public float getTotalLeaves()
	{
		return totalLeaves;
	}
	public void setTotalLeaves(float totalLeaves)
	{
		this.totalLeaves = totalLeaves;
	}
	public long getLocationId()
	{
		return locationId;
	}
	public void setLocationId(long locationId)
	{
		this.locationId = locationId;
	}
	public long getUserId()
	{
		return userId;
	}
	public void setUserId(long userId)
	{
		this.userId = userId;
	}
	public long getLeaveTypeId()
	{
		return leaveTypeId;
	}
	public void setLeaveTypeId(long leaveTypeId)
	{
		this.leaveTypeId = leaveTypeId;
	}
	public String getLeavetypeRoles()
	{
		return leavetypeRoles;
	}
	public void setLeavetypeRoles(String leavetypeRoles)
	{
		this.leavetypeRoles = leavetypeRoles;
	}
	public String getLeavetypeDepartment()
	{
		return leavetypeDepartment;
	}
	public void setLeavetypeDepartment(String leavetypeDepartment)
	{
		this.leavetypeDepartment = leavetypeDepartment;
	}
	public String getLeavetypeEmpType()
	{
		return leavetypeEmpType;
	}
	public void setLeavetypeEmpType(String leavetypeEmpType)
	{
		this.leavetypeEmpType = leavetypeEmpType;
	}
	 
	 
}
