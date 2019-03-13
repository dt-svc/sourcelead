package com.bis.sourcelead.dto;

import java.util.List;

public class TimesheetProjectTaskDTO {
	
	private Long userId;
	
	private String locationCode;
	
	private String taskUUID;
	
	private Long projectId;
	
   private Long projectTaskMemberId;
	
	private String timesheetDate;
	
	private String hours;
	
	private String comments;
	
	private List<FileInfo> fileInfoList;
	
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

	public Long getProjectTaskMemberId() {
		return projectTaskMemberId;
	}

	public void setProjectTaskMemberId(Long projectTaskMemberId) {
		this.projectTaskMemberId = projectTaskMemberId;
	}

	public String getTimesheetDate() {
		return timesheetDate;
	}

	public void setTimesheetDate(String timesheetDate) {
		this.timesheetDate = timesheetDate;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<FileInfo> getFileInfoList() {
		return fileInfoList;
	}

	public void setFileInfoList(List<FileInfo> fileInfoList) {
		this.fileInfoList = fileInfoList;
	}


	
}
