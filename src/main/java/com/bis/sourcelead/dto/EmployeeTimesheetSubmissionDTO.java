package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.entity.ProjectTasks;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EmployeeTimesheetSubmissionDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long projectMemberId;
	
	private Long projectId;
	
	private Long projectTaskId;
	
	private String ticketNumber;
	
	private String hourlyType;
	
	private String projectTaskType;
	
	private List<ProjectTasks> projectTaskList;
	
	private List<TimesheetEntriesDTO> timesheetEntries;

	private String timesheetStatus;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getProjectTaskId() {
		return projectTaskId;
	}

	public void setProjectTaskId(Long projectTaskId) {
		this.projectTaskId = projectTaskId;
	}
	
	public List<TimesheetEntriesDTO> getTimesheetEntries() {
		return timesheetEntries;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public void setTimesheetEntries(List<TimesheetEntriesDTO> timesheetEntries) {
		this.timesheetEntries = timesheetEntries;
	}

	public List<ProjectTasks> getProjectTaskList() {
		return projectTaskList;
	}

	public void setProjectTaskList(List<ProjectTasks> projectTaskList) {
		this.projectTaskList = projectTaskList;
	}

	public String getTimesheetStatus() {
		return timesheetStatus;
	}

	public void setTimesheetStatus(String timesheetStatus) {
		this.timesheetStatus = timesheetStatus;
	}

	public String getProjectTaskType() {
		return projectTaskType;
	}

	public void setProjectTaskType(String projectTaskType) {
		this.projectTaskType = projectTaskType;
	}

	public String getHourlyType() {
		return hourlyType;
	}

	public void setHourlyType(String hourlyType) {
		this.hourlyType = hourlyType;
	}

	
}
