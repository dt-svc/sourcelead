package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

	
	public class AvailableAssociatesDTO extends SearchDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String associates;
	private long id;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private String firstName;
	private String projectName;
	
	private String candidateName;
    private Date fromDate;
	
	private Date toDate;
	
	private List<Long> userIds;
	
	private List<Long> projectsIds;
	
	private String empId;
	
	private String empName;
	
	private BigDecimal percentageOfWork;
	
	private String projectStartDate;
	
	private String projectEndDate;
	
	private String empStartDate;
	
	private String empEndDate;
	
	private String availableHours;
	
	private String hoursperWeek;
	
	private String type;
	
	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	private int orgWorkingHours;
	
	
	
	public int getOrgWorkingHours() {
		return orgWorkingHours;
	}

	public void setOrgWorkingHours(int orgWorkingHours) {
		this.orgWorkingHours = orgWorkingHours;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getPercentageOfWork() {
		return percentageOfWork;
	}

	public void setPercentageOfWork(BigDecimal percentageOfWork) {
		this.percentageOfWork = percentageOfWork;
	}



	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
  

	public String getAvailableHours() {
		return availableHours;
	}

	public void setAvailableHours(String availableHours) {
		this.availableHours = availableHours;
	}

	public String getHoursperWeek() {
		return hoursperWeek;
	}

	public void setHoursperWeek(String hoursperWeek) {
		this.hoursperWeek = hoursperWeek;
	}

	public List<Long> getProjectsIds() {
		return projectsIds;
	}

	public void setProjectsIds(List<Long> projectsIds) {
		this.projectsIds = projectsIds;
	}

	public String getAssociates() {
		return associates;
	}

	public void setAssociates(String associates) {
		this.associates = associates;
	}


	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	public String getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public String getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String getEmpStartDate() {
		return empStartDate;
	}

	public void setEmpStartDate(String empStartDate) {
		this.empStartDate = empStartDate;
	}

	public String getEmpEndDate() {
		return empEndDate;
	}

	public void setEmpEndDate(String empEndDate) {
		this.empEndDate = empEndDate;
	}
	
	
}
