package com.bis.sourcelead.entity;

import java.sql.Date;

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
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "TIMESHEET_ENTRIES")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class TimesheetEntries {
	
	 
	
	@Id
    @Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TIMESHEET_SUBMISSION_ID", nullable=false ) 
	private TimesheetSubmissions timesheetSubmissions;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "TOTAL_HOURS")
	private Integer totalHours;
	
	 
	@Column(name = "SUBMITTED_HOURS")
	private Integer submittedHours;
	  
	@Column(name = "ENTRY_STATUS")
	private String entryStatus;
	
	 
	@Column(name = "MON_TASK")
	@Type(type="text")
	private String monTask;
	 
	@Column(name = "MON_HOURS")
    private Integer monHours;
	  
	@Column(name = "MON_COMMENTS")
	@Type(type="text")
    private String moncomments;
	 
	 
	@Column(name = "TUE_TASK")
	@Type(type="text")
	private String tueTask;
	  
	@Column(name = "TUE_HOURS")
	private Integer tueHours;
	  
	@Column(name = "TUE_COMMENTS")
	@Type(type="text")
	private String tuecomments;
	  
	@Column(name = "WED_TASK")
	@Type(type="text")
	private String wedTask;
	  
	@Column(name = "WED_HOURS")
	private Integer wedHours;
	  
	@Column(name = "WED_COMMENTS")
	@Type(type="text")
	private String wedcomments;
	  
    @Column(name = "THU_TASK")
    @Type(type="text")
	private String thuTask;
	  
	@Column(name = "THU_HOURS")
	private Integer thuHours;
	  
	@Column(name = "THU_COMMENTS")
	@Type(type="text")
	private String thucomments;
	  
	@Column(name = "FRI_TASK")
	@Type(type="text")
	private String friTask;
	  
	@Column(name = "FRI_HOURS")
	private Integer friHours;
	  
	@Column(name = "FRI_COMMENTS")
	@Type(type="text")
	private String fricomments;
	  
	@Column(name = "SAT_TASK")
	@Type(type="text")
	private String satTask;
	  
	@Column(name = "SAT_HOURS")
	private Integer satHours;
	  
	@Column(name = "SAT_COMMENTS")
	@Type(type="text")
	private String satcomments;
	  
	@Column(name = "SUN_TASK")
	@Type(type="text")
	private String sunTask;
	  
	@Column(name = "SUN_HOURS")
	private Integer sunHours;
	  
	@Column(name = "SUN_COMMENTS")
	@Type(type="text")
	private String suncomments;
	  
	@Column(name = "ENTRY_TYPE")
	private String entryType;
	  
	@Column(name = "STATUS")
	private String status;
	  
	@Column(name = " CREATED_DATE")
    private Date createdDate; 
		
    @Column(name = "CREATED_BY")
	private String createdBy;
		
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


	@JsonIgnore
	public TimesheetSubmissions getTimesheetSubmissions() {
		return timesheetSubmissions;
	}

	public void setTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions) {
		this.timesheetSubmissions = timesheetSubmissions;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(Integer totalHours) {
		this.totalHours = totalHours;
	}

	public Integer getSubmittedHours() {
		return submittedHours;
	}

	public void setSubmittedHours(Integer submittedHours) {
		this.submittedHours = submittedHours;
	}

	public String getEntryStatus() {
		return entryStatus;
	}

	public void setEntryStatus(String entryStatus) {
		this.entryStatus = entryStatus;
	}

	public String getMonTask() {
		return monTask;
	}

	public void setMonTask(String monTask) {
		this.monTask = monTask;
	}

	public Integer getMonHours() {
		return monHours;
	}

	public void setMonHours(Integer monHours) {
		this.monHours = monHours;
	}

	public String getMoncomments() {
		return moncomments;
	}

	public void setMoncomments(String moncomments) {
		this.moncomments = moncomments;
	}

	public String getTueTask() {
		return tueTask;
	}

	public void setTueTask(String tueTask) {
		this.tueTask = tueTask;
	}

	public Integer getTueHours() {
		return tueHours;
	}

	public void setTueHours(Integer tueHours) {
		this.tueHours = tueHours;
	}

	public String getTuecomments() {
		return tuecomments;
	}

	public void setTuecomments(String tuecomments) {
		this.tuecomments = tuecomments;
	}

	public String getWedTask() {
		return wedTask;
	}

	public void setWedTask(String wedTask) {
		this.wedTask = wedTask;
	}

	public Integer getWedHours() {
		return wedHours;
	}

	public void setWedHours(Integer wedHours) {
		this.wedHours = wedHours;
	}

	public String getWedcomments() {
		return wedcomments;
	}

	public void setWedcomments(String wedcomments) {
		this.wedcomments = wedcomments;
	}

	public String getThuTask() {
		return thuTask;
	}

	public void setThuTask(String thuTask) {
		this.thuTask = thuTask;
	}

	public Integer getThuHours() {
		return thuHours;
	}

	public void setThuHours(Integer thuHours) {
		this.thuHours = thuHours;
	}

	public String getThucomments() {
		return thucomments;
	}

	public void setThucomments(String thucomments) {
		this.thucomments = thucomments;
	}

	public String getFriTask() {
		return friTask;
	}

	public void setFriTask(String friTask) {
		this.friTask = friTask;
	}

	public Integer getFriHours() {
		return friHours;
	}

	public void setFriHours(Integer friHours) {
		this.friHours = friHours;
	}

	public String getFricomments() {
		return fricomments;
	}

	public void setFricomments(String fricomments) {
		this.fricomments = fricomments;
	}

	public String getSatTask() {
		return satTask;
	}

	public void setSatTask(String satTask) {
		this.satTask = satTask;
	}

	public Integer getSatHours() {
		return satHours;
	}

	public void setSatHours(Integer satHours) {
		this.satHours = satHours;
	}

	public String getSatcomments() {
		return satcomments;
	}

	public void setSatcomments(String satcomments) {
		this.satcomments = satcomments;
	}

	public String getSunTask() {
		return sunTask;
	}

	public void setSunTask(String sunTask) {
		this.sunTask = sunTask;
	}

	public Integer getSunHours() {
		return sunHours;
	}

	public void setSunHours(Integer sunHours) {
		this.sunHours = sunHours;
	}

	public String getSuncomments() {
		return suncomments;
	}

	public void setSuncomments(String suncomments) {
		this.suncomments = suncomments;
	}

	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
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
