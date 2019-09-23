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
@Table(name = "UPLOAD_XLS_HISTORY")
public class UploadXlsHistory implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	
	@Column(name = "TOTAL_COUNT")
	private int totalCount;
	
	@Column(name = "SUCCESS_COUNT")
	private int successCount;
	
	@Column(name = "FAILURE_COUNT")
	private int failureCount;
	
	@Column(name = "UPLOAD_ENTITY_TYPE")
	private String uploadEntityType;
	
	@Column(name = "UPLOAD_ID")
	private String uploadId;
	
	@Column(name = "FILE_NAME", columnDefinition = "LONGTEXT default NULL")
	private String fileName;
	
	@Column(name = "FILE_URL", columnDefinition = "LONGTEXT default NULL")
	private String fileUrl;
	
	@Column(name = "FILE_CONTENT_TYPE", columnDefinition = "LONGTEXT default NULL")
	private String fileContentType;
	
	@Column(name = "UPLOAD_STATUS")
	private String uploadStatus;
	
	@Column(name = "SESSION_ID")
	private String sessionId;
	
	@Column(name = "UPLOAD_PROCESSING_STATUS")
	private String uploadProcessingStatus;
	
	@Column(name = "UUID")
	private String UUID;

	@Column(name = "STATUS")
	private Integer status;
	
    @Column(name = "CREATED_DATE")
	private Date createdDate;
    
    @Column(name = "END_DATE")
  	private Date endDate;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}

	public String getUploadId() {
		return uploadId;
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
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

	public String getUploadEntityType() {
		return uploadEntityType;
	}

	public void setUploadEntityType(String uploadEntityType) {
		this.uploadEntityType = uploadEntityType;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUploadProcessingStatus() {
		return uploadProcessingStatus;
	}

	public void setUploadProcessingStatus(String uploadProcessingStatus) {
		this.uploadProcessingStatus = uploadProcessingStatus;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
    
    

	}
