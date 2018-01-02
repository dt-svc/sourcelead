package com.bis.sourcelead.dto;

import java.util.Date;
import java.util.List;

public class TimesheetProjectMemberDTO {
	
	private Long projectMemberId;
	
	private String timesheetDate;
	
	private String hours;
	
	private String comments;
	
	private List<FileInfo> fileInfoList;

	public Long getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
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

	public String getTimesheetDate() {
		return timesheetDate;
	}

	public void setTimesheetDate(String timesheetDate) {
		this.timesheetDate = timesheetDate;
	}

	
	
}
