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

/**
 * @author Prasad K
 *		
 */
@Entity
@Table(name = "CANDIDATE_DOCUMENTS_HISTORY")
public class CandidateDocumentsHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_DOCUMENTS", nullable = false)
	private CandidateDocuments candidateDocuments;
	@Column(name = "DOC_UPLOAD_STATUS")
	private String docUploadStatus;
	@Column(name = "IS_REQUESTED_DOC_REQUIRED")
	private String isRequestedDocRequired;
	@Column(name = "REQUESTED_DOC_STATUS ")
	private String requestedDocStatus;
	@Column(name = "REQUESTED_DOC_REJECTED_REASON")
	@Type(type = "text")
	private String requestedDocRejectedReason;
	@Column(name = "DOCUMENT_TYPE ")
	private String documentType;
	@Column(name = "DOCUMENT_OTHER_TYPE ")
	private String documentOtherType;
	@Column(name = "DESCRIPTION ")
	@Type(type = "text")
	private String description;
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
	@Column(name = "STATUS ")
	private Integer status;
	@Column(name = "IS_CONFIDENTIAL")
	private String isConfidential;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_USER")
	private User createdUser;
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getDocumentType()
	{
		return documentType;
	}
	
	public void setDocumentType(String documentType)
	{
		this.documentType = documentType;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getFileContentType()
	{
		return fileContentType;
	}
	
	public void setFileContentType(String fileContentType)
	{
		this.fileContentType = fileContentType;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	public String getDocumentUUID()
	{
		return documentUUID;
	}
	
	public void setDocumentUUID(String documentUUID)
	{
		this.documentUUID = documentUUID;
	}
	
	public Date getCreatedDate()
	{
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}
	
	public String getCreatedBy()
	{
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	
	public String getDocumentOtherType()
	{
		return documentOtherType;
	}
	
	public void setDocumentOtherType(String documentOtherType)
	{
		this.documentOtherType = documentOtherType;
	}
	
	public Integer getStatus()
	{
		return status;
	}
	
	public void setStatus(Integer status)
	{
		this.status = status;
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
	
	public String getIsConfidential()
	{
		return isConfidential;
	}
	
	public void setIsConfidential(String isConfidential)
	{
		this.isConfidential = isConfidential;
	}
	
	public CandidateDocuments getCandidateDocuments()
	{
		return candidateDocuments;
	}
	
	public void setCandidateDocuments(CandidateDocuments candidateDocuments)
	{
		this.candidateDocuments = candidateDocuments;
	}
	
	public User getCreatedUser()
	{
		return createdUser;
	}
	
	public void setCreatedUser(User createdUser)
	{
		this.createdUser = createdUser;
	}
}
