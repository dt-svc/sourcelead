package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.entity.ProjectTasks;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EmployeeTimesheetFileInfoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long projectMemberId;
	
	private String weekComment;
	
	private String timesheetStatus;
	
	private List<FileInfo> fileInfo;

	public Long getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public String getWeekComment() {
		return weekComment;
	}

	public void setWeekComment(String weekComment) {
		this.weekComment = weekComment;
	}

	public String getTimesheetStatus() {
		return timesheetStatus;
	}

	public void setTimesheetStatus(String timesheetStatus) {
		this.timesheetStatus = timesheetStatus;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	
	

	
}
