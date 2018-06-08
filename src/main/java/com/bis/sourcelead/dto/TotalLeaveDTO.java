package com.bis.sourcelead.dto;

public class TotalLeaveDTO {
	
	private String leaveTypeId;

	private String noOfLeaves;
	
	private String userId;
	
	public String getNoOfLeaves()
	{
		return noOfLeaves;
	}
	
	public void setNoOfLeaves(String noOfLeaves)
	{
		this.noOfLeaves = noOfLeaves;
	}

	public String getLeaveTypeId()
	{
		return leaveTypeId;
	}

	public void setLeaveTypeId(String leaveTypeId)
	{
		this.leaveTypeId = leaveTypeId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	
}