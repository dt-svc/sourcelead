package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "PROJECT_MEMBERS")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProjectMembers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROJECT_ID", nullable=false )	
	private Projects projects;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="EMPLOYEE_ID", nullable=false )	
	private Employees employee;
	
	@Column(name = "TIMESHEET_FREQUENCY")
	private String timesheetFrequency;
	
	@Column(name = "PROJECT_ASSIGNED_DATE")
    private Date projectAssignedDate;
	
	@Column(name = "CURRENT_BILL_RATE")
    private Float currentBillRate;
	
	@Column(name = "RATE_TYPE")
	private String rateType;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_DATE")
    private Date createdDate;
    
    @Column(name = "CREATED_BY")
    private String createdBy;
    
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    @Column(name = "UPDATED_BY")
    private String updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public Projects getProjects() {
		return projects;
	}

	public void setProjects(Projects projects) {
		this.projects = projects;
	}
	
	@JsonIgnore
	public Employees getEmployee() {
		return employee;
	}

	public void setEmployee(Employees employee) {
		this.employee = employee;
	}

	public String getTimesheetFrequency() {
		return timesheetFrequency;
	}

	public void setTimesheetFrequency(String timesheetFrequency) {
		this.timesheetFrequency = timesheetFrequency;
	}

	public Date getProjectAssignedDate() {
		return projectAssignedDate;
	}

	public void setProjectAssignedDate(Date projectAssignedDate) {
		this.projectAssignedDate = projectAssignedDate;
	}

	public Float getCurrentBillRate() {
		return currentBillRate;
	}

	public void setCurrentBillRate(Float currentBillRate) {
		this.currentBillRate = currentBillRate;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
