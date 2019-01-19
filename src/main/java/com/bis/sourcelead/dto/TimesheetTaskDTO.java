package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.bis.sourcelead.annotations.Required;

public class TimesheetTaskDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Required(desc="id")
	private Long id;
	
	@Required(desc="taskName")
	private String taskName;
	
	@Required(desc="taskType")
	private String taskType;
	
	private String ticketNumber;

	private String generatedId;
	
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

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	

	
}
