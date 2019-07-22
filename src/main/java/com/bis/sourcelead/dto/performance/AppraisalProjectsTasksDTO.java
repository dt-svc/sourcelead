package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.util.Map;

public class AppraisalProjectsTasksDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer totalProjectTasks;
	
	private String projectName;
	
	private String startDate;
	
	private String endDate;
	
	private Map<String,String> assignProjectsMap;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Map<String, String> getAssignProjectsMap() {
		return assignProjectsMap;
	}

	public void setAssignProjectsMap(Map<String, String> assignProjectsMap) {
		this.assignProjectsMap = assignProjectsMap;
	}

	public Integer getTotalProjectTasks() {
		return totalProjectTasks;
	}

	public void setTotalProjectTasks(Integer totalProjectTasks) {
		this.totalProjectTasks = totalProjectTasks;
	}

	
	
	
	
}
