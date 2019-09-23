package com.bis.sourcelead.entity;


import java.io.Serializable;
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
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TIMESHEET_DAY_ENTRIES_HISTORY")
public class TimeSheetDayEntriesHistory implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ENTRY_ID") 
	private TimeSheetDayEntries timeSheetDayEntries;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SUBMISSION_ID") 
	private TimesheetSubmissions timesheetSubmissions;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "PROJECT_TASK_ID")
	private ProjectTasks projectTask;
	
	@NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "TIMESHEET_DATE")
	private Date timesheetDate;
	
	@Column(name = "BILLABLE_HOURS")
	private String billableHours;
	
	@Column(name = "HOURLY_TYPE")
	private String hourlyType;
	 
	@Column(name = "NONBILLABLE_HOURS")
	private String nonBillableHours;
	  
	@Column(name = "TOTALHOURS")
	private String totalHours;
	 
	@Column(name ="TASK_TYPE")
	private String taskType;
	
	@Column(name = "COMMENTS", columnDefinition = "TEXT default NULL")
    private String comments;

	@Column(name="STATUS")
	private Integer status;
	
	@Column(name = "ACTIVITY_ID")
	private Long timesheetActivityId;
	
	@NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "CREATED_DATE")
	private Date createdDate;
		
    @Column(name = "CREATED_BY")
	private String createdBy;
		
    @NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "UPDATED_DATE")
    private Date updatedDate; 
		
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public TimeSheetDayEntries getTimeSheetDayEntries() {
		return timeSheetDayEntries;
	}

	public void setTimeSheetDayEntries(TimeSheetDayEntries timeSheetDayEntries) {
		this.timeSheetDayEntries = timeSheetDayEntries;
	}

	public TimesheetSubmissions getTimesheetSubmissions() {
		return timesheetSubmissions;
	}

	public void setTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions) {
		this.timesheetSubmissions = timesheetSubmissions;
	}

	public ProjectTasks getProjectTask() {
		return projectTask;
	}

	public void setProjectTask(ProjectTasks projectTask) {
		this.projectTask = projectTask;
	}

	public Date getTimesheetDate() {
		return timesheetDate;
	}

	public void setTimesheetDate(Date timesheetDate) {
		this.timesheetDate = timesheetDate;
	}

	public String getBillableHours() {
		return billableHours;
	}

	public void setBillableHours(String billableHours) {
		this.billableHours = billableHours;
	}

	public String getNonBillableHours() {
		return nonBillableHours;
	}

	public void setNonBillableHours(String nonBillableHours) {
		this.nonBillableHours = nonBillableHours;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getTimesheetActivityId() {
		return timesheetActivityId;
	}

	public void setTimesheetActivityId(Long timesheetActivityId) {
		this.timesheetActivityId = timesheetActivityId;
	}

	public String getHourlyType() {
		return hourlyType;
	}

	public void setHourlyType(String hourlyType) {
		this.hourlyType = hourlyType;
	}
	
}
