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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TIMESHEET_SUBMISSIONS_DOCUMENTS_HISTORY")
public class TimesheetSubmissionsDocumentsHistory implements Serializable{
	
	 
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIMESHEET_SUBMISSION_ID", nullable=false)
	private TimesheetSubmissions timesheetSubmissions;
	
	@Column(name = "DOC_FILE",columnDefinition="mediumblob")
	@Lob
	private  byte[] docFile;
		
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	
	@Column(name = "FILE_NAME", columnDefinition = "TEXT default NULL")
	private String fileName;
	
	@NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = " CREATED_DATE")
	private Date createdDate; 
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	  
	@NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "UPDATED_DATE")
	private Date updatedDate; 
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "FILE_URL")
	private String fileUrl;
	
	@Column(name = "TIMESHEET_ACTIVITY_ID")
	private Long timesheetActivity;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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

	public TimesheetSubmissions getTimesheetSubmissions() {
		return timesheetSubmissions;
	}

	public void setTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions) {
		this.timesheetSubmissions = timesheetSubmissions;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Long getTimesheetActivity() {
		return timesheetActivity;
	}

	public void setTimesheetActivity(Long timesheetActivity) {
		this.timesheetActivity = timesheetActivity;
	}

	
}
