package com.bis.sourcelead.dto;

public class DayWiseTimesheetReoprtsListDTO {
	private String timesheetDate;
	private String employeeName;
	private String employeeEmail;
	private String ticketNo;
	private String projectName;
	private String task;
	private String billableHrs	;
	private String nonBillableHrs;
	private String status;
	public String getTimesheetDate() {
		return timesheetDate;
	}
	public void setTimesheetDate(String timesheetDate) {
		this.timesheetDate = timesheetDate;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getBillableHrs() {
		return billableHrs;
	}
	public void setBillableHrs(String billableHrs) {
		this.billableHrs = billableHrs;
	}
	public String getNonBillableHrs() {
		return nonBillableHrs;
	}
	public void setNonBillableHrs(String nonBillableHrs) {
		this.nonBillableHrs = nonBillableHrs;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
