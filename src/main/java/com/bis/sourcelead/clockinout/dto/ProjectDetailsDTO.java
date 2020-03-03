package com.bis.sourcelead.clockinout.dto;

import java.io.Serializable;

public class ProjectDetailsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private String projectName;
	
	private String dateFormat;
	
	private String projectStartDate;
	
	private String projectEndDate;
	
	private String projectTimeSheetFrequency;
	
	private String client;
	
	private String managerName;
	
	

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public String getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String getProjectTimeSheetFrequency() {
		return projectTimeSheetFrequency;
	}

	public void setProjectTimeSheetFrequency(String projectTimeSheetFrequency) {
		this.projectTimeSheetFrequency = projectTimeSheetFrequency;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	
	

}
