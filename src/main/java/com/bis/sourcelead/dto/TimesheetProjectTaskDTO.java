package com.bis.sourcelead.dto;

import java.util.List;

public class TimesheetProjectTaskDTO {
	
	private Long userId;
	
	private String locationCode;
	
	private String taskUUID;
	
	private Long projectId;
	
	private List<TimesheetProjectMemberDTO> assignedMember;
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getTaskUUID() {
		return taskUUID;
	}

	public void setTaskUUID(String taskUUID) {
		this.taskUUID = taskUUID;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public List<TimesheetProjectMemberDTO> getAssignedMember() {
		return assignedMember;
	}

	public void setAssignedMember(List<TimesheetProjectMemberDTO> assignedMember) {
		this.assignedMember = assignedMember;
	}
	
}
