package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.bis.sourcelead.constants.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PROJECT_DOCUMENTS")
public class ProjectDocuments implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "FILE_TYPE")
	private String fileType;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PROJECT_ID", nullable=false )	
	private Projects projects;
	
	@Lob
    @Column(name="FILE_CONTENTS",length=100000)
    private byte [] fileContents;

	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "FILE_URL")
	@Type(type="text")
	private String fileUrl;

	@Column(name = "FILE_FORMAT")
	private String fileFormat;

	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_FOLDER_ID")
   	private ProjectDocumentFolder projectFolderId;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
   	private User createdBy;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY")
   	private User updatedBy;
	
	@Column(name = "CREATED_NAME")
	private String createdByName;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_NAME")
	private String updatedByName;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Transient
    @JsonProperty
    private String createdDateStr;
	
	@Transient
    @JsonProperty
	private String permissionAccess;
	
	
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
	
	

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Projects getProjects() {
		return projects;
	}

	public void setProjects(Projects projects) {
		this.projects = projects;
	}

	public byte[] getFileContents() {
		return fileContents;
	}

	public void setFileContents(byte[] fileContents) {
		this.fileContents = fileContents;
	}
	
	public ProjectDocuments(){
		
	}

	public ProjectDocuments(String fileName, String fileURL, String fileType, Projects projects) {
		super();
		this.fileName = fileName;
		this.fileUrl = fileURL;
		this.fileType = fileType;
		this.projects = projects;
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

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public ProjectDocumentFolder getProjectFolderId() {
		return projectFolderId;
	}

	public void setProjectFolderId(ProjectDocumentFolder projectFolderId) {
		this.projectFolderId = projectFolderId;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}
	
	public String getCreatedDateStr() {
		if(createdDate != null){
			SimpleDateFormat dateFormat = projects.getOrgLocationsId().getLocFullFormDateFormatter();
			createdDateStr = dateFormat.format(createdDate);
		}
		return createdDateStr;
	}

	public String getPermissionAccess() {
		return permissionAccess;
	}

	public void setPermissionAccess(String permissionAccess) {
		this.permissionAccess = permissionAccess;
	}
}
