package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.bis.sourcelead.enums.OnboardStatus;

@Entity
@Table(name = "ON_BOARDING_TEMPLATE_DOCUMENTS")
public class OnBoardingTemplateDocuments implements Serializable{

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ON_BOARDING_TEMPLATE_ID", nullable = false)
	private OnBoardingTemplate onboardingTemplateId;
    
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_ON_BOARDING_ID", nullable = false)
	private CandidateOnBoarding candidateOnBoardingId;
    
    @Column(name = "DOCUMENT_TYPE")
	private String documentType;
    
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_NAME")
	@Type(type="text")
	private String fileName;
	
	@Column(name = "UPLOADED_DOC_FILE_CONTENT_TYPE")
	private String uploadedFileContentType;
	
	@Column(name = "UPLOADED_DOC_FILE_NAME")
	@Type(type="text")
	private String uploadedDocFileName;
	
	@Column(name = "DOCUMENT_UUID")
	private String documentUUID;
	
	@Column(name = "DOCUMENT_STATUS")
	@Enumerated(EnumType.STRING)
	private OnboardStatus documentStatus;
	
    @Column(name = "STATUS")
	private Integer status;
		
    @Column(name = "UPLOAD_FILE_URL", columnDefinition = "TEXT default NULL")
	private String uploadFileURL;
    
    @Column(name = "FILE_URL", columnDefinition = "TEXT default NULL")
   	private String fileURL;
    
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	private String onboardDocumentType;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OnBoardingTemplate getOnboardingTemplateId() {
		return onboardingTemplateId;
	}

	public void setOnboardingTemplateId(OnBoardingTemplate onboardingTemplateId) {
		this.onboardingTemplateId = onboardingTemplateId;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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

	public String getDocumentUUID() {
		return documentUUID;
	}

	public void setDocumentUUID(String documentUUID) {
		this.documentUUID = documentUUID;
	}

	public CandidateOnBoarding getCandidateOnBoardingId() {
		return candidateOnBoardingId;
	}

	public void setCandidateOnBoardingId(CandidateOnBoarding candidateOnBoardingId) {
		this.candidateOnBoardingId = candidateOnBoardingId;
	}

	public String getOnboardDocumentType() {
		return onboardDocumentType;
	}

	public void setOnboardDocumentType(String onboardDocumentType) {
		this.onboardDocumentType = onboardDocumentType;
	}

	public OnboardStatus getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(OnboardStatus documentStatus) {
		this.documentStatus = documentStatus;
	}

	public String getUploadedFileContentType()
	{
		return uploadedFileContentType;
	}

	public void setUploadedFileContentType(String uploadedFileContentType)
	{
		this.uploadedFileContentType = uploadedFileContentType;
	}

	public String getUploadedDocFileName()
	{
		return uploadedDocFileName;
	}

	public void setUploadedDocFileName(String uploadedDocFileName)
	{
		this.uploadedDocFileName = uploadedDocFileName;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public String getUploadFileURL() {
		return uploadFileURL;
	}

	public void setUploadFileURL(String uploadFileURL) {
		this.uploadFileURL = uploadFileURL;
	}
}
