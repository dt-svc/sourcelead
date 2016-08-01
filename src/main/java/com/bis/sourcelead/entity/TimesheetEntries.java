package com.bis.sourcelead.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "TIMESHEET_ENTRIES")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="timeSheetEntries")
public class TimesheetEntries {
	
	 
	
	@Id
    @Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="TIMESHEET_SUBMISSION_ID") 
	private TimesheetSubmissions timesheetSubmissions;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "PROJECT_TASK")
	private ProjectTasks projectTask;
	
	@NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "START_DATE")
	private Date startDate;
	
	@NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "TOTAL_HOURS")
	private Integer totalHours;
	
	 
	@Column(name = "SUBMITTED_HOURS")
	private Integer submittedHours;
	  
	@Column(name = "ENTRY_STATUS")
	private String entryStatus;
	
	 
	@Column(name = "MON_HOURS")
    private String monHours;
	  
	@Column(name = "MON_COMMENTS")
	@Type(type="text")
    private String moncomments;
	 
	  
	@Column(name = "TUE_HOURS")
	private String tueHours;
	  
	@Column(name = "TUE_COMMENTS")
	@Type(type="text")
	private String tuecomments;
	  
	  
	@Column(name = "WED_HOURS")
	private String wedHours;
	  
	@Column(name = "WED_COMMENTS")
	@Type(type="text")
	private String wedcomments;
	  
	  
	@Column(name = "THU_HOURS")
	private String thuHours;
	  
	@Column(name = "THU_COMMENTS")
	@Type(type="text")
	private String thucomments;
	  
	@Column(name = "FRI_HOURS")
	private String friHours;
	  
	@Column(name = "FRI_COMMENTS")
	@Type(type="text")
	private String fricomments;
	  
	@Column(name = "SAT_HOURS")
	private String satHours;
	  
	@Column(name = "SAT_COMMENTS")
	@Type(type="text")
	private String satcomments;
	
	@Column(name = "SUN_HOURS")
	private String sunHours;
	  
	@Column(name = "SUN_COMMENTS")
	@Type(type="text")
	private String suncomments;
	  
	@Column(name = "ENTRY_TYPE")
	private String entryType;
	  
	@Column(name = "STATUS")
	private String status;
	  
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
	

    @Column(name = "DOC_FILE",columnDefinition="mediumblob")
	@Lob
	private  byte[] docFile;
		
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	
	@Column(name = "WEEK_COMMENTS")
	@Type(type="text")
	private String weekcomments;
	
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
    
	@JsonIgnore
	public ProjectTasks getProjectTask() {
		return projectTask;
	}

	public void setProjectTask(ProjectTasks projectTask) {
		this.projectTask = projectTask;
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

	public String getMonHours() {
		return monHours;
	}

	public void setMonHours(String monHours) {
		this.monHours = monHours;
	}

	public String getMoncomments() {
		return moncomments;
	}

	public void setMoncomments(String moncomments) {
		this.moncomments = moncomments;
	}

	public String getTueHours() {
		return tueHours;
	}

	public void setTueHours(String tueHours) {
		this.tueHours = tueHours;
	}

	public String getTuecomments() {
		return tuecomments;
	}

	public void setTuecomments(String tuecomments) {
		this.tuecomments = tuecomments;
	}

	public String getWedHours() {
		return wedHours;
	}

	public void setWedHours(String wedHours) {
		this.wedHours = wedHours;
	}

	public String getWedcomments() {
		return wedcomments;
	}

	public void setWedcomments(String wedcomments) {
		this.wedcomments = wedcomments;
	}

	public String getThuHours() {
		return thuHours;
	}

	public void setThuHours(String thuHours) {
		this.thuHours = thuHours;
	}

	public String getThucomments() {
		return thucomments;
	}

	public void setThucomments(String thucomments) {
		this.thucomments = thucomments;
	}

	public String getFriHours() {
		return friHours;
	}

	public void setFriHours(String friHours) {
		this.friHours = friHours;
	}

	public String getFricomments() {
		return fricomments;
	}

	public void setFricomments(String fricomments) {
		this.fricomments = fricomments;
	}

	public String getSatHours() {
		return satHours;
	}

	public void setSatHours(String satHours) {
		this.satHours = satHours;
	}

	public String getSatcomments() {
		return satcomments;
	}

	public void setSatcomments(String satcomments) {
		this.satcomments = satcomments;
	}

	public String getSunHours() {
		return sunHours;
	}

	public void setSunHours(String sunHours) {
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

	public byte[] getDocFile() {
		return docFile;
	}

	public void setDocFile(byte[] docFile) {
		this.docFile = docFile;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getWeekcomments() {
		return weekcomments;
	}

	public void setWeekcomments(String weekcomments) {
		this.weekcomments = weekcomments;
	}
	 
	 
}
