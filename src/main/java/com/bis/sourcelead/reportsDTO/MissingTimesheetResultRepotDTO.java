package com.bis.sourcelead.reportsDTO;

public class MissingTimesheetResultRepotDTO {

	private String employeName;
	
	private String employeType;
	
	private String projectName;
	
	private String frequency;
	
	private String managerName;
	
	private Long dueDays;
	
	private String submittedOn;

	public String getEmployeName() {
		return employeName;
	}

	public String getEmployeType() {
		return employeType;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getFrequency() {
		return frequency;
	}

	public String getManagerName() {
		return managerName;
	}


	public void setEmployeName(String employeName) {
		this.employeName = employeName;
	}

	public void setEmployeType(String employeType) {
		this.employeType = employeType;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public Long getDueDays() {
		return dueDays;
	}

	public void setDueDays(Long dueDays) {
		this.dueDays = dueDays;
	}

	public String getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(String submittedOn) {
		this.submittedOn = submittedOn;
	}
}
