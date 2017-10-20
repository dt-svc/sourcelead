package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class TimesheetEntryDetailsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<ProjectMembersDTO> projectMembersList;
	
	private Long projectMemberId;
	
	private List<String> timeFrequencies;
	
	private String currentFrequency;
	
	private TimeSubFrequencyDTO timeSubFrequencies;
	
	private String timesheetStatus;
	
	private String rejectedReason;

	public List<ProjectMembersDTO> getProjectMembersList() {
		return projectMembersList;
	}

	public void setProjectMembersList(List<ProjectMembersDTO> projectMembersList) {
		this.projectMembersList = projectMembersList;
	}

	public Long getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public List<String> getTimeFrequencies() {
		return timeFrequencies;
	}

	public void setTimeFrequencies(List<String> timeFrequencies) {
		this.timeFrequencies = timeFrequencies;
	}

	public String getCurrentFrequency() {
		return currentFrequency;
	}

	public void setCurrentFrequency(String currentFrequency) {
		this.currentFrequency = currentFrequency;
	}

	public TimeSubFrequencyDTO getTimeSubFrequencies() {
		return timeSubFrequencies;
	}

	public void setTimeSubFrequencies(TimeSubFrequencyDTO timeSubFrequencies) {
		this.timeSubFrequencies = timeSubFrequencies;
	}

	public String getTimesheetStatus() {
		return timesheetStatus;
	}

	public void setTimesheetStatus(String timesheetStatus) {
		this.timesheetStatus = timesheetStatus;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}
	
	
	
	
    
}
