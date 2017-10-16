package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class TimesheetEntryDetailsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	List<ProjectMembersDTO> projectMembersList;
	
	Long projectMemberId;
	
	String frequencyStartDate;
	
	List<TimesheetEntriesDTO> timesheetFrequencyList;
	
	
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

	public List<TimesheetEntriesDTO> getTimesheetFrequencyList() {
		return timesheetFrequencyList;
	}

	public void setTimesheetFrequencyList(List<TimesheetEntriesDTO> timesheetFrequencyList) {
		this.timesheetFrequencyList = timesheetFrequencyList;
	}
	
    
}
