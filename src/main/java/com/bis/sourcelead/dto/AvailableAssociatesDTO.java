package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

	
	public class AvailableAssociatesDTO extends SearchDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String associates;
	
	private String projects;
	
    private Date fromDate;
	
	private Date toDate;
	
	private List<Long> userIds;
	
	private List<Long> projectsIds;
	
	private Long empId;
	
	private String empName;
	
	private String projectDuration;
	
	private Date empStartDate;
	
	private Date empEndDate;
	
	private BigDecimal availability;
	
	private BigDecimal hoursperWeek;
	
	private BigDecimal total;
	

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

	public String getProjectDuration() {
		return projectDuration;
	}

	public void setProjectDuration(String projectDuration) {
		this.projectDuration = projectDuration;
	}

	public Date getEmpStartDate() {
		return empStartDate;
	}

	public void setEmpStartDate(Date empStartDate) {
		this.empStartDate = empStartDate;
	}

	public Date getEmpEndDate() {
		return empEndDate;
	}

	public void setEmpEndDate(Date empEndDate) {
		this.empEndDate = empEndDate;
	}

	public BigDecimal getAvailability() {
		return availability;
	}

	public void setAvailability(BigDecimal availability) {
		this.availability = availability;
	}

	public BigDecimal getHoursperWeek() {
		return hoursperWeek;
	}

	public void setHoursperWeek(BigDecimal hoursperWeek) {
		this.hoursperWeek = hoursperWeek;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
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

	public String getProjects() {
		return projects;
	}

	public void setProjects(String projects) {
		this.projects = projects;
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
	
	
	
	
	
}
