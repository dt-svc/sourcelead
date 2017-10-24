package com.bis.sourcelead.dto;

import java.io.Serializable;

public class TimesheetEntriesDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
    private TimesheetTaskDTO timesheetTaskDTO;
    
    private String dayOfTheDate;
    
    private String date;
    
    private boolean isEditable;
    
    private String hours;
    
    private String comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TimesheetTaskDTO getTimesheetTaskDTO() {
		return timesheetTaskDTO;
	}

	public void setTimesheetTaskDTO(TimesheetTaskDTO timesheetTaskDTO) {
		this.timesheetTaskDTO = timesheetTaskDTO;
	}

	public String getDayOfTheDate() {
		return dayOfTheDate;
	}

	public void setDayOfTheDate(String dayOfTheDate) {
		this.dayOfTheDate = dayOfTheDate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}
