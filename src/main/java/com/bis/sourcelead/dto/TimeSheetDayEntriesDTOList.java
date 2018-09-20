package com.bis.sourcelead.dto;

public class TimeSheetDayEntriesDTOList {
	private long timesheetEntryId;
	private String employeeName;
	private String approvalStatus; 
	private String timesheetDate;
	private String hours;
	private String comments;
	private Long projectMemberId;
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
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
	public Long getProjectMemberId() {
		return projectMemberId;
	}
	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
	}
	public long getTimesheetEntryId() {
		return timesheetEntryId;
	}
	public void setTimesheetEntryId(long timesheetEntryId) {
		this.timesheetEntryId = timesheetEntryId;
	}
	
	
}
