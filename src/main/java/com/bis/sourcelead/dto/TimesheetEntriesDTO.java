package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.bis.sourcelead.annotations.Required;

public class TimesheetEntriesDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long submissionId;
	
	private Long ticketNumber;
	
	private Long projectMemberId;
	
	//@Required(desc="timesheetTaskDTO")
    private TimesheetTaskDTO timesheetTaskDTO;
    
    private String dayOfTheDate;
    
    @Required(desc="date")
    private String date;
    
    private boolean editable;
    
    @Required(desc="hours",timesheetHours=true)
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

	public boolean getEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
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

	public Long getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}

	public Long getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public Long getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(Long ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	
	
}
