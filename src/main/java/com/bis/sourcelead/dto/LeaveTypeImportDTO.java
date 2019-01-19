package com.bis.sourcelead.dto;

public class LeaveTypeImportDTO {
	private String associateName;
	private String associateEmail;
	private String leaveTypeName;
	private String numberOfLeaves;
	public String getAssociateName()
	{
		return associateName;
	}
	public String getAssociateEmail()
	{
		return associateEmail;
	}
	public String getLeaveTypeName()
	{
		return leaveTypeName;
	}
	public String getNumberOfLeaves()
	{
		return numberOfLeaves;
	}
	public void setAssociateName(String associateName)
	{
		this.associateName = associateName;
	}
	public void setAssociateEmail(String associateEmail)
	{
		this.associateEmail = associateEmail;
	}
	public void setLeaveTypeName(String leaveTypeName)
	{
		this.leaveTypeName = leaveTypeName;
	}
	public void setNumberOfLeaves(String numberOfLeaves)
	{
		this.numberOfLeaves = numberOfLeaves;
	}
	
}
