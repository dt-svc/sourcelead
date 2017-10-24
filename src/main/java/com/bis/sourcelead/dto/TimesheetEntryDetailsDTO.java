package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TimesheetEntryDetailsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<ProjectMembersDTO> projectMembersList;
	
	private Long projectMemberId;
	
	private Long projectId;
	
	private Long masterCandidateId;
	
    private Map<String,String> timeFrequencies;
	
	private String currentTimeFrequency;
	
	private Map<String,String> timeSubFrequencies;
	
	private String currentTimeSubFrequency;
	
	private TimeSheetSubmissionDTO timesheetSubmission;
	

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
	public Map<String, String> getTimeFrequencies() {
		return timeFrequencies;
	}

	public void setTimeFrequencies(Map<String, String> timeFrequencies) {
		this.timeFrequencies = timeFrequencies;
	}
	public String getCurrentTimeFrequency() {
		return currentTimeFrequency;
	}

	public void setCurrentTimeFrequency(String currentTimeFrequency) {
		this.currentTimeFrequency = currentTimeFrequency;
	}
	public Map<String, String> getTimeSubFrequencies() {
		return timeSubFrequencies;
	}

	public void setTimeSubFrequencies(Map<String, String> timeSubFrequencies) {
		this.timeSubFrequencies = timeSubFrequencies;
	}

	public String getCurrentTimeSubFrequency() {
		return currentTimeSubFrequency;
	}

	public void setCurrentTimeSubFrequency(String currentTimeSubFrequency) {
		this.currentTimeSubFrequency = currentTimeSubFrequency;
	}


	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getMasterCandidateId() {
		return masterCandidateId;
	}

	public void setMasterCandidateId(Long masterCandidateId) {
		this.masterCandidateId = masterCandidateId;
	}

	public TimeSheetSubmissionDTO getTimesheetSubmission() {
		return timesheetSubmission;
	}

	public void setTimesheetSubmission(TimeSheetSubmissionDTO timesheetSubmission) {
		this.timesheetSubmission = timesheetSubmission;
	}

	

    
}
