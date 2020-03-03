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
	
	private String manager1;
	
	private String manager2;
	
	private String projectStatus;
	
	private String manager1ImgUrl;
	
	private String manager2ImgUrl;

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

	public String getManager1() {
		return manager1;
	}

	public void setManager1(String manager1) {
		this.manager1 = manager1;
	}

	public String getManager2() {
		return manager2;
	}

	public void setManager2(String manager2) {
		this.manager2 = manager2;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getManager1ImgUrl() {
		return manager1ImgUrl;
	}

	public void setManager1ImgUrl(String manager1ImgUrl) {
		this.manager1ImgUrl = manager1ImgUrl;
	}

	public String getManager2ImgUrl() {
		return manager2ImgUrl;
	}

	public void setManager2ImgUrl(String manager2ImgUrl) {
		this.manager2ImgUrl = manager2ImgUrl;
	}


}
