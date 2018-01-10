package com.bis.sourcelead.dto;

/**
 * @author BIS115
 *
 */
public class ImportTimesheetDTO {

	private String employeeId;
	private String employeeName;
	private String employeeEmail;
	private Long projectId;
	private Long projectMemberId;
	private String project;
	private String taskName;
	private String clientName;
	private String actualStartDate;
	private String actualEndDate;
	private String totalHours;
	private String totalBillableHours;
	private String totalNonBillableHours;
	private String submissionStatus;
	private String week1;
	private String week2;
	private String week3;
	private String week4;
	private String week5;
	private String weekComments;
	
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
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getActualStartDate() {
		return actualStartDate;
	}
	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}
	public String getActualEndDate() {
		return actualEndDate;
	}
	public void setActualEndDate(String actualEndDate) {
		this.actualEndDate = actualEndDate;
	}
	public String getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}
	public String getTotalBillableHours() {
		return totalBillableHours;
	}
	public void setTotalBillableHours(String totalBillableHours) {
		this.totalBillableHours = totalBillableHours;
	}
	public String getTotalNonBillableHours() {
		return totalNonBillableHours;
	}
	public void setTotalNonBillableHours(String totalNonBillableHours) {
		this.totalNonBillableHours = totalNonBillableHours;
	}
	public String getSubmissionStatus() {
		return submissionStatus;
	}
	public void setSubmissionStatus(String submissionStatus) {
		this.submissionStatus = submissionStatus;
	}
	public Long getProjectMemberId() {
		return projectMemberId;
	}
	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
	}
	public String getWeek1() {
		return week1;
	}
	public void setWeek1(String week1) {
		this.week1 = week1;
	}
	public String getWeek2() {
		return week2;
	}
	public void setWeek2(String week2) {
		this.week2 = week2;
	}
	public String getWeek3() {
		return week3;
	}
	public void setWeek3(String week3) {
		this.week3 = week3;
	}
	public String getWeek4() {
		return week4;
	}
	public void setWeek4(String week4) {
		this.week4 = week4;
	}
	public String getWeek5() {
		return week5;
	}
	public void setWeek5(String week5) {
		this.week5 = week5;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getWeekComments() {
		return weekComments;
	}
	public void setWeekComments(String weekComments) {
		this.weekComments = weekComments;
	}
	
	
}
