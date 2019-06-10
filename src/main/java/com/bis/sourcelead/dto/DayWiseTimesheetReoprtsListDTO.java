package com.bis.sourcelead.dto;

import java.math.BigInteger;
import java.util.Date;

public class DayWiseTimesheetReoprtsListDTO {
	private BigInteger timeEntryId;
	private String timesheetDate;
	private String employeeId;
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
	private String departmentName;
	private String country;
	private String state;
	private String city;
	private String candidateLocation;
	private BigInteger projectId;
	private BigInteger masterCandidateId;
	private Date submittedDate;
	private Date approvedDate;
	private Date sahFrequenchStartDate;
	private Date sahFrequenchEndDate;
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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
	public String getTimesheetDate() {
		return timesheetDate;
	}
	public void setTimesheetDate(String timesheetDate) {
		this.timesheetDate = timesheetDate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCandidateLocation() {
		return candidateLocation;
	}
	public void setCandidateLocation(String candidateLocation) {
		this.candidateLocation = candidateLocation;
	}
	public BigInteger getProjectId() {
		return projectId;
	}
	public void setProjectId(BigInteger projectId) {
		this.projectId = projectId;
	}
	public BigInteger getMasterCandidateId() {
		return masterCandidateId;
	}
	public void setMasterCandidateId(BigInteger masterCandidateId) {
		this.masterCandidateId = masterCandidateId;
	}
	public Date getSahFrequenchStartDate() {
		return sahFrequenchStartDate;
	}
	public void setSahFrequenchStartDate(Date sahFrequenchStartDate) {
		this.sahFrequenchStartDate = sahFrequenchStartDate;
	}
	public Date getSahFrequenchEndDate() {
		return sahFrequenchEndDate;
	}
	public void setSahFrequenchEndDate(Date sahFrequenchEndDate) {
		this.sahFrequenchEndDate = sahFrequenchEndDate;
	}
	public Date getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
}
