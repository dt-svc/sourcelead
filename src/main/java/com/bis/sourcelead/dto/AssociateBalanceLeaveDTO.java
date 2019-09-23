package com.bis.sourcelead.dto;

import java.util.List;

/**
 * @author BIS
 *
 */
public class AssociateBalanceLeaveDTO {
	
	private String id;
	
	private String assoBalLeaveUUID;
	
	private String createdBy;
	
	private String updatedBy;
	
	private String leaveTypeName;
	
	private String noofLeaves;
	
	private String userId;
	
	private Long leaveTypeId;
	
	private float balanceLeaveCounts;
	
	private Long orgLocationId;
	
	private List<TotalLeaveDTO> totalLeavesDto;
	
	private float appliedLeaveCount;
	
	private float assBalLeaveHis;

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

	public float getBalanceLeaveCounts()
	{
		return balanceLeaveCounts;
	}

	public void setBalanceLeaveCounts(float balanceLeaveCounts)
	{
		this.balanceLeaveCounts = balanceLeaveCounts;
	}

	public Long getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public float getAppliedLeaveCount()
	{
		return appliedLeaveCount;
	}

	public void setAppliedLeaveCount(float appliedLeaveCount)
	{
		this.appliedLeaveCount = appliedLeaveCount;
	}

	public float getAssBalLeaveHis() {
		return assBalLeaveHis;
	}

	public void setAssBalLeaveHis(float assBalLeaveHis) {
		this.assBalLeaveHis = assBalLeaveHis;
	}
	
}
