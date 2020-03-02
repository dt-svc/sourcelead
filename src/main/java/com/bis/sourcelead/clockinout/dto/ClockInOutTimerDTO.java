package com.bis.sourcelead.clockinout.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class ClockInOutTimerDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigInteger id;
	
	private String projectName;
	
	private String taskName;
	
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

}
