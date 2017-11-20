package com.bis.sourcelead.dto;

import java.io.Serializable;

public class TimesheetTaskDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String taskName;
	
	private String taskType;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	

	
}
