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

@Entity
@Table(name = "XLS_BULK_UPLOAD_HISTORY")
public class XlsBulkUploadHistroy implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "UPLOAD_ENTITY_TYPE")
	private String uploadEntityType;
	
	@Column(name = "DOCUMENT_KEY")
	private String documentKey;
	
	@Column(name = "ORG_LOCATION", columnDefinition = "LONGTEXT default NULL")
	private String orgLocation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	
	@Column(name = "FILE_URL", columnDefinition = "LONGTEXT default NULL")
	private String fileUrl;
	
	@Column(name = "FAILURE_FILE_URL", columnDefinition = "LONGTEXT default NULL")
	private String failureFileUrl;
	
	@Column(name = "UPLOAD_TYPE")
	private String uploadType;
	
	@Column(name = "UPLOAD_STATUS")
	private String uploadStatus;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "FAILED_COUNT")
	private int failedCount;
	
	@Column(name = "STATUS")
	private Integer status;
	
    @Column(name = "CREATED_DATE")
	private Date createdDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY", nullable = false)
	private User createdBy;
    
    @Column(name = "UPDATED_DATE")
   	private Date updatedDate;
       
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "UPDATED_BY", nullable = false)
   	private User updatedBy;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUploadEntityType() {
		return uploadEntityType;
	}

	public void setUploadEntityType(String uploadEntityType) {
		this.uploadEntityType = uploadEntityType;
	}

	public String getDocumentKey() {
		return documentKey;
	}

	public void setDocumentKey(String documentKey) {
		this.documentKey = documentKey;
	}

	public String getOrgLocation() {
		return orgLocation;
	}

	public void setOrgLocation(String orgLocation) {
		this.orgLocation = orgLocation;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}

	public String getFailureFileUrl() {
		return failureFileUrl;
	}

	public void setFailureFileUrl(String failureFileUrl) {
		this.failureFileUrl = failureFileUrl;
	}
	
	
}
