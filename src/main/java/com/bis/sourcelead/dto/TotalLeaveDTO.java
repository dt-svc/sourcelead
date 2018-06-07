package com.bis.sourcelead.dto;

public class TotalLeaveDTO {
	
	private String leaveTypeId;

	private String noOfLeaves;
	
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
	
	
}