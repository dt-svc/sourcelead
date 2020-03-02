package com.bis.sourcelead.clockinout.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class ClockInOutTimerDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigInteger id;
	
	private String projectName;
	
	private String taskName;
	
	private String dateFormat;
	
	private String projectStartDate;
	
	private String projectEndDate;
	
	private String projectTimeSheetFrequency;
	
	// url means jobTitle. 
	private String url;
	

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

}
