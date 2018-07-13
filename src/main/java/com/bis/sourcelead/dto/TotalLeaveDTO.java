package com.bis.sourcelead.dto;

public class TotalLeaveDTO {
	
	private String leaveTypeId;

	private String noOfLeaves;
	
	private String userId;
	
	private String leavetypeRoles;
	
	private String leavetypeDepartment;
	
	private String leavetypeEmpType;
	
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

	public String getLeavetypeRoles() {
		return leavetypeRoles;
	}

	public String getLeavetypeDepartment() {
		return leavetypeDepartment;
	}

	public String getLeavetypeEmpType() {
		return leavetypeEmpType;
	}

	public void setLeavetypeRoles(String leavetypeRoles) {
		this.leavetypeRoles = leavetypeRoles;
	}

	public void setLeavetypeDepartment(String leavetypeDepartment) {
		this.leavetypeDepartment = leavetypeDepartment;
	}

	public void setLeavetypeEmpType(String leavetypeEmpType) {
		this.leavetypeEmpType = leavetypeEmpType;
	}
}