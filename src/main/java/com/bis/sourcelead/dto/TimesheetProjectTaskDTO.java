package com.bis.sourcelead.dto;

import java.util.List;

public class TimesheetProjectTaskDTO {
	
	private Long userId;
	
	private String locationCode;
	
	private Long projectTaskId;
	
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

	public Long getProjectTaskId() {
		return projectTaskId;
	}

	public void setProjectTaskId(Long projectTaskId) {
		this.projectTaskId = projectTaskId;
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
