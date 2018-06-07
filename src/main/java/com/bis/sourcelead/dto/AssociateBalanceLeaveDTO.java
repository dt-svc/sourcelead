package com.bis.sourcelead.dto;

import java.util.List;

public class AssociateBalanceLeaveDTO {
	
	private String id;
	
	private String assoBalLeaveUUID;
	
	private String createdBy;
	
	private String updatedBy;
	
	private String leaveTypeName;
	
	private String noofLeaves;
	
	private String userId;
	
	private Long leaveTypeId;
	
	
	private List<TotalLeaveDTO> totalLeavesDto;

	public String getId()
	{
		return id;
	}

	public String getAssoBalLeaveUUID()
	{
		return assoBalLeaveUUID;
	}

	public String getCreatedBy()
	{
		return createdBy;
	}

	public String getUpdatedBy()
	{
		return updatedBy;
	}

	public List<TotalLeaveDTO> getTotalLeavesDto()
	{
		return totalLeavesDto;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setAssoBalLeaveUUID(String assoBalLeaveUUID)
	{
		this.assoBalLeaveUUID = assoBalLeaveUUID;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public void setTotalLeavesDto(List<TotalLeaveDTO> totalLeavesDto)
	{
		this.totalLeavesDto = totalLeavesDto;
	}

	public String getLeaveTypeName()
	{
		return leaveTypeName;
	}

	public String getNoofLeaves()
	{
		return noofLeaves;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setLeaveTypeName(String leaveTypeName)
	{
		this.leaveTypeName = leaveTypeName;
	}

	public void setNoofLeaves(String noofLeaves)
	{
		this.noofLeaves = noofLeaves;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public Long getLeaveTypeId()
	{
		return leaveTypeId;
	}

	public void setLeaveTypeId(Long leaveTypeId)
	{
		this.leaveTypeId = leaveTypeId;
	}
	
	
}
