package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.entity.User;

public class UserGroupDTO extends SearchDTO{
	
	private Long id;
	
	@Required(desc = "Group Id")
	private String groupId;
	
	@Required(desc = "Group Name")
	private String groupName;
	
	private String department;
	
	private String userId;
	
	@Required(desc = "Pay Frequency")
	private String payFrequency;
	
	private String description;
	
	private String createdBy;
	
	private String updatedBy;
	
	private String createdDate;
	
	private String updatedDate;
	
	private String associateIds;
	
	private String numOfAssociates;
	
	private String groupUUID;
	
	private String fromType;
	
	private int totalEmpCount;
	
	private List<User> userList;
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getGroupId()
	{
		return groupId;
	}
	
	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}
	
	public String getGroupName()
	{
		return groupName;
	}
	
	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}
	
	public String getDepartment()
	{
		return department;
	}
	
	public void setDepartment(String department)
	{
		this.department = department;
	}
	
	public String getUserId()
	{
		return userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getPayFrequency()
	{
		return payFrequency;
	}
	
	public void setPayFrequency(String payFrequency)
	{
		this.payFrequency = payFrequency;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getCreatedBy()
	{
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	
	public String getUpdatedBy()
	{
		return updatedBy;
	}
	
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}
	
	public String getCreatedDate()
	{
		return createdDate;
	}
	
	public void setCreatedDate(String createdDate)
	{
		this.createdDate = createdDate;
	}
	
	public String getUpdatedDate()
	{
		return updatedDate;
	}
	
	public void setUpdatedDate(String updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public String getAssociateIds()
	{
		return associateIds;
	}

	public void setAssociateIds(String associateIds)
	{
		this.associateIds = associateIds;
	}

	public String getNumOfAssociates()
	{
		return numOfAssociates;
	}

	public void setNumOfAssociates(String numOfAssociates)
	{
		this.numOfAssociates = numOfAssociates;
	}

	public String getGroupUUID()
	{
		return groupUUID;
	}

	public void setGroupUUID(String groupUUID)
	{
		this.groupUUID = groupUUID;
	}

	public List<User> getUserList()
	{
		return userList;
	}

	public void setUserList(List<User> userList)
	{
		this.userList = userList;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public int getTotalEmpCount() {
		return totalEmpCount;
	}

	public void setTotalEmpCount(int totalEmpCount) {
		this.totalEmpCount = totalEmpCount;
	}
}
