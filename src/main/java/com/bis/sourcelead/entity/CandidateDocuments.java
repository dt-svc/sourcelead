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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

/**
 * @author Prasad K
 *
 */

@Entity
@Table(name = "CANDIDATE_DOCUMENTS")
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "candidateDocuments")
public class CandidateDocuments implements Serializable{
	
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )        
    private OrgLocations orgLocationId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REQUESTED_DOC_ID")        
    private RequestedDocuments requestedDocumentsId;
	
	@Column(name = "DOC_UPLOAD_STATUS")
	private String docUploadStatus;
	
	@Column(name = "IS_REQUESTED_DOC_REQUIRED")
	private String isRequestedDocRequired;

	@Column(name = "REQUESTED_DOC_STATUS ")
	private String requestedDocStatus;
	
	@Column(name = "REQUESTED_DOC_REJECTED_REASON")
	@Type(type="text")
	private String requestedDocRejectedReason;
	
	
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID", nullable=false)
	private CandidateGlobal candidateId;
	
	@Column(name = "DOCUMENT_TYPE ")
	private String documentType;
	
	@Column(name = "DOCUMENT_OTHER_TYPE ")
	private String documentOtherType;
	
	@Column(name = "DESCRIPTION ")
	@Type(type="text")
	private String description ;
	
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_NAME", columnDefinition = "TEXT default NULL")
	private String fileName;
	
	@Column(name = "DOCUMENT_UUID ")
	private String documentUUID;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = " UPDATED_DATE ")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "STATUS ")
	private Integer status;
	
	@Column(name="IS_CONFIDENTIAL")
	private String isConfidential;
	
	@Column(name = "FILE_URL")
	private String fileURL;
	
	@Transient
	private Long userIdStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public CandidateGlobal getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(CandidateGlobal candidateId) {
		this.candidateId = candidateId;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public String getDocumentUUID() {
		return documentUUID;
	}

	public void setDocumentUUID(String documentUUID) {
		this.documentUUID = documentUUID;
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

	public String getDocumentOtherType() {
		return documentOtherType;
	}

	public void setDocumentOtherType(String documentOtherType) {
		this.documentOtherType = documentOtherType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public RequestedDocuments getRequestedDocumentsId()
	{
		return requestedDocumentsId;
	}

	public void setRequestedDocumentsId(RequestedDocuments requestedDocumentsId)
	{
		this.requestedDocumentsId = requestedDocumentsId;
	}

	public String getIsRequestedDocRequired()
	{
		return isRequestedDocRequired;
	}

	public void setIsRequestedDocRequired(String isRequestedDocRequired)
	{
		this.isRequestedDocRequired = isRequestedDocRequired;
	}

	public String getDocUploadStatus()
	{
		return docUploadStatus;
	}

	public void setDocUploadStatus(String docUploadStatus)
	{
		this.docUploadStatus = docUploadStatus;
	}

	public String getRequestedDocStatus()
	{
		return requestedDocStatus;
	}

	public void setRequestedDocStatus(String requestedDocStatus)
	{
		this.requestedDocStatus = requestedDocStatus;
	}

	public String getRequestedDocRejectedReason()
	{
		return requestedDocRejectedReason;
	}

	public void setRequestedDocRejectedReason(String requestedDocRejectedReason)
	{
		this.requestedDocRejectedReason = requestedDocRejectedReason;
	}

	public String getIsConfidential() {
		return isConfidential;
	}

	public void setIsConfidential(String isConfidential) {
		this.isConfidential = isConfidential;
	}

	public Long getUserIdStr() {
		return userIdStr;
	}

	public void setUserIdStr(Long userIdStr) {
		this.userIdStr = userIdStr;
	}

	public String getFileURL()
	{
		return fileURL;
	}

	public void setFileURL(String fileURL)
	{
		this.fileURL = fileURL;
	}

	
}
