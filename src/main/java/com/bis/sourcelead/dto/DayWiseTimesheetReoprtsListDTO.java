package com.bis.sourcelead.dto;

import java.math.BigInteger;
import java.util.Date;

public class DayWiseTimesheetReoprtsListDTO {
	private BigInteger timeEntryId;
	private Date timesheetDate;
	private String employeeName;
	private String employeeEmail;
	private String ticketNo;
	private String projectName;
	private String task;
	private String billableHrs	;
	private String nonBillableHrs;
	private String status;
	private Double billableTotalCount;
	private Double nonBillableTotalCount;
	private String submittedOn;
	private String approvedOn;
	private String comments;
	
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
	public Double getBillableTotalCount() {
		return billableTotalCount;
	}
	public void setBillableTotalCount(Double billableTotalCount) {
		this.billableTotalCount = billableTotalCount;
	}
	public Double getNonBillableTotalCount() {
		return nonBillableTotalCount;
	}
	public void setNonBillableTotalCount(Double nonBillableTotalCount) {
		this.nonBillableTotalCount = nonBillableTotalCount;
	}
	public String getSubmittedOn() {
		return submittedOn;
	}
	public void setSubmittedOn(String submittedOn) {
		this.submittedOn = submittedOn;
	}
	public String getApprovedOn() {
		return approvedOn;
	}
	public void setApprovedOn(String approvedOn) {
		this.approvedOn = approvedOn;
	}
	
	public BigInteger getTimeEntryId() {
		return timeEntryId;
	}
	public void setTimeEntryId(BigInteger timeEntryId) {
		this.timeEntryId = timeEntryId;
	}
	public Date getTimesheetDate() {
		return timesheetDate;
	}
	public void setTimesheetDate(Date timesheetDate) {
		this.timesheetDate = timesheetDate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
