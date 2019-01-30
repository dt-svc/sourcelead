package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

	
	public class AvailableAssociatesDTO extends SearchDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String associates;
	
	private String projectName;
	
    private Date fromDate;
	
	private Date toDate;
	
	private List<Long> userIds;
	
	private List<Long> projectsIds;
	
	private Long empId;
	
	private String empName;
	
	private BigDecimal percentageOfWork;
	
	private String projectStartDate;
	
	private String projectEndDate;
	
	private String empStartDate;
	
	private String empEndDate;
	
	private BigDecimal availableHours;
	
	private BigDecimal hoursperWeek;
	
	public BigDecimal getPercentageOfWork() {
		return percentageOfWork;
	}

	public void setPercentageOfWork(BigDecimal percentageOfWork) {
		this.percentageOfWork = percentageOfWork;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public BigDecimal getAvailableHours() {
		return availableHours;
	}

	public void setAvailableHours(BigDecimal availableHours) {
		this.availableHours = availableHours;
	}

	public BigDecimal getHoursperWeek() {
		return hoursperWeek;
	}

	public void setHoursperWeek(BigDecimal hoursperWeek) {
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
