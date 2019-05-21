package com.bis.sourcelead.reportsDTO;

public class MissingTimesheetResultRepotDTO {

	private String employeName;
	
	private String employeType;
	
	private String projectName;
	
	private String frequency;
	
	private String managerName;
	
	private Long dueDays;
	
	private String submittedOn;
	
	private String manager1;
	
	private String manager2;
	
	private String approvalMechanism;
	
	private String noOfHours;

	public String getNoOfHours() {
		return noOfHours;
	}

	public void setNoOfHours(String noOfHours) {
		this.noOfHours = noOfHours;
	}

	public String getApprovalMechanism() {
		return approvalMechanism;
	}

	public void setApprovalMechanism(String approvalMechanism) {
		this.approvalMechanism = approvalMechanism;
	}

	public String getManager1() {
		return manager1;
	}

	public void setManager1(String manager1) {
		this.manager1 = manager1;
	}

	public String getManager2() {
		return manager2;
	}

	public void setManager2(String manager2) {
		this.manager2 = manager2;
	}

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
