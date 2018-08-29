package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CANDIDATE_IDENTITIES")
public class CandidateIdendities {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="CANDIDATE_ID",nullable=false)        
	private Candidate candidateId;
	
	@Column(name = "ID_NUMBER")
	private String idNumber;
	
	@Column(name = "ID_TYPE")
	private String idType;
	
	@Column(name = "START_DATE ")
	private Date startDate;
	
	@Column(name = "END_DATE ")
	private Date endDate;
	
	@Column(name = "CURR_STATUS")
	private String currentStatus;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "FILE_URL")
	private String fileUrl;
	
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "IS_REQUIRED")
	private String isRequired;
	
	@Column(name = "IS_MULTIPLE_REQUIRED")
	private String isMultipleRequired;
	
	@Column(name = "DOCUMENT_UUID")
	private String documentUUID;
	
	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;
	
	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;
	
	@Column(name = "CREATED_DATE ")
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE ")
	private Date updatedDate;
	
	@Column(name = "DOCUMENT_TYPE ")
	private Long documentType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
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

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
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

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public String getIsMultipleRequired() {
		return isMultipleRequired;
	}

	public void setIsMultipleRequired(String isMultipleRequired) {
		this.isMultipleRequired = isMultipleRequired;
	}

	public String getDocumentUUID() {
		return documentUUID;
	}

	public void setDocumentUUID(String documentUUID) {
		this.documentUUID = documentUUID;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getDocumentType() {
		return documentType;
	}

	public void setDocumentType(Long documentType) {
		this.documentType = documentType;
	}

	
	
	

}
