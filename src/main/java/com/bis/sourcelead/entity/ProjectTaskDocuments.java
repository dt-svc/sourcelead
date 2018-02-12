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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PROJECT_TASK_DOCUMENTS")
public class ProjectTaskDocuments implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "DOCUMENT_NAME")
	private String documentName;
	
	@Column(name = "FILE_TYPE")
	private String fileType;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PROJECT_TASK_ID", nullable=false )	
	private ProjectTasks projectTaskId;
	
	@Lob
    @Column(name="FILE_CONTENTS",length=100000)
    private byte [] fileContents;

	@Column(name = "DESCRIPTION")
	@Type(type="text")
	private String description;
	
	@Column(name = "STATUS")
	private Integer status;
	
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "CREATED_DATE")
    private Date createdDate; 
		
    @Column(name = "CREATED_BY")
	private String createdBy;
		
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "UPDATED_DATE")
    private Date updatedDate; 
		
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "FILE_URL")
	private String fileURL;
	
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	 
	public byte[] getFileContents() {
		return fileContents;
	}

	public void setFileContents(byte[] fileContents) {
		this.fileContents = fileContents;
	}
	
	public ProjectTaskDocuments(){
		
	}

	public ProjectTaskDocuments(String fileName, String fileURL, String fileType, ProjectTasks projectTasks) {
		super();
		this.fileName = fileName;
		this.fileURL = fileURL;
		this.fileType = fileType;
		this.projectTaskId = projectTasks;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ProjectTasks getProjectTaskId() {
		return projectTaskId;
	}

	public void setProjectTaskId(ProjectTasks projectTaskId) {
		this.projectTaskId = projectTaskId;
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

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}
}
