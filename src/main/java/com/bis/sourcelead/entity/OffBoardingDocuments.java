package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@Table(name = "OFF_BOARDING_DOCUMENTS")
public class OffBoardingDocuments implements Serializable{

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_OFF_BOARDING_ID", nullable = false)
	private CandidateOffBoarding candidateOffBoardingId;
    
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OFF_BOARDING_TEMPLATE_ID", nullable = false)
	private OffBoardingTemplate offboardingTemplateId;
    
    
    @Column(name = "DOCUMENT_TYPE")
	private String documentType;
    
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_NAME")
	@Type(type="text")
	private String fileName;
	
	@Column(name = "DOCUMENT_UUID")
	private String documentUUID;
	
	@Column(name = "DOCUMENT_STATUS")
	@Enumerated(EnumType.STRING)
	private OnboardStatus documentStatus;
	
    @Column(name = "STATUS")
	private Integer status;
		
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "FILE_URL", columnDefinition = "TEXT default NULL")
	private String fileURL;
	
	@Transient
	private String offboardDocumentType;
	
	@Transient
	private List<OffBoardingDocuments> resignationDocumentsList;
	
	@Transient
	private List<OffBoardingDocuments> offboardPackDocumentsList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CandidateOffBoarding getCandidateOffBoardingId() {
		return candidateOffBoardingId;
	}

	public void setCandidateOffBoardingId(CandidateOffBoarding candidateOffBoardingId) {
		this.candidateOffBoardingId = candidateOffBoardingId;
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

	public String getDocumentUUID() {
		return documentUUID;
	}

	public void setDocumentUUID(String documentUUID) {
		this.documentUUID = documentUUID;
	}

	public OnboardStatus getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(OnboardStatus documentStatus) {
		this.documentStatus = documentStatus;
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

	public OffBoardingTemplate getOffboardingTemplateId() {
		return offboardingTemplateId;
	}

	public void setOffboardingTemplateId(OffBoardingTemplate offboardingTemplateId) {
		this.offboardingTemplateId = offboardingTemplateId;
	}

	public String getOffboardDocumentType() {
		return offboardDocumentType;
	}

	public void setOffboardDocumentType(String offboardDocumentType) {
		this.offboardDocumentType = offboardDocumentType;
	}

	public List<OffBoardingDocuments> getResignationDocumentsList() {
		return resignationDocumentsList;
	}

	public void setResignationDocumentsList(List<OffBoardingDocuments> resignationDocumentsList) {
		this.resignationDocumentsList = resignationDocumentsList;
	}

	public List<OffBoardingDocuments> getOffboardPackDocumentsList() {
		return offboardPackDocumentsList;
	}

	public void setOffboardPackDocumentsList(List<OffBoardingDocuments> offboardPackDocumentsList) {
		this.offboardPackDocumentsList = offboardPackDocumentsList;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}
}
