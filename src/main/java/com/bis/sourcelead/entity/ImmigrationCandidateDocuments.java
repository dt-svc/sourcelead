package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.json.JSONArray;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "IMMIGRATION_CANDIDATE_DOCS")
public class ImmigrationCandidateDocuments implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	
	@ManyToOne
	@JoinColumn(name = "IMMIGRATION_TEMPLATE_ID")
	private ImmigrationTemplate immigrationTemplateId;
	
	@ManyToOne() 
	@JoinColumn(name = "IMMIGRATION_ID")
	private Immigration immigrationId;
	
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID")
	private Candidate candidateId;
	
	@ManyToOne
	@JoinColumn(name = "DOCUMENT_TYPE")
	private LookUpData documentType;
	
	@Column(name = "DOCUMENT_OTHER_TYPE ")
	private String documentOtherType;
	
	@Column(name = "DOCUMENT_UUID ")
	private String docUUID;
	
	@Column(name = "DESCRIPTION",columnDefinition = "TEXT default NULL")
	private String description;

	@Column(name = "IS_REQUIRED ")
	private String isRequired;
	
	@Column(name = "ALLOW_MULTIPLE")
	private String allowMultiple;
	
	@Column(name="DOCUMENT_STATUS")
	private String documentStatus;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "IS_TEMPALTE_CONFIGURED")
	private String isTemplateConfigured;
	
	@Column(name = "REQUEST_FILE_URL",columnDefinition = "TEXT default NULL")
	private String requestFileUrl;
	
	@Column(name = "UPLOADED_FILE_URL",columnDefinition = "TEXT default NULL")
	private String uplodedFileUrl;
	
	@Column(name = "REQUEST_FILE_NAME")
	private String requestFileName;
	
	@Column(name = "UPLOADED_FILE_NAME")
	private String upldedFileName;
	
	@Column(name = "REQUEST_FILE_CONTENT_TYPE")
	private String requestFileContentType;
	
	@Column(name = "UPLOADED_FILE_CONTENT_TYPE")
	private String upldedFileContentType;
	
	@Column(name = "CREATED_DATE ")
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;
	
	@Column(name = "DOCUMENT_CATEGORY")
	private String docCategory;
	
	@Transient
	@JsonProperty
	private String documentTypeName;
	
	@Transient
	@JsonProperty
	private JSONArray docsHistoryList;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="immigrationCandidateDocumentId",orphanRemoval=true)
	private List<ImmigrationCandidateDocumentsHistory> candidateDocumentsHistories;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ImmigrationTemplate getImmigrationTemplateId() {
		return immigrationTemplateId;
	}

	public void setImmigrationTemplateId(ImmigrationTemplate immigrationTemplateId) {
		this.immigrationTemplateId = immigrationTemplateId;
	}

	public Immigration getImmigrationId() {
		return immigrationId;
	}

	public void setImmigrationId(Immigration immigrationId) {
		this.immigrationId = immigrationId;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}

	public String getDocUUID() {
		return docUUID;
	}

	public void setDocUUID(String docUUID) {
		this.docUUID = docUUID;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRequestFileUrl() {
		return requestFileUrl;
	}

	public void setRequestFileUrl(String requestFileUrl) {
		this.requestFileUrl = requestFileUrl;
	}

	public String getUplodedFileUrl() {
		return uplodedFileUrl;
	}

	public void setUplodedFileUrl(String uplodedFileUrl) {
		this.uplodedFileUrl = uplodedFileUrl;
	}

	public String getRequestFileName() {
		return requestFileName;
	}

	public void setRequestFileName(String requestFileName) {
		this.requestFileName = requestFileName;
	}

	public String getUpldedFileName() {
		return upldedFileName;
	}

	public void setUpldedFileName(String upldedFileName) {
		this.upldedFileName = upldedFileName;
	}

	public String getRequestFileContentType() {
		return requestFileContentType;
	}

	public void setRequestFileContentType(String requestFileContentType) {
		this.requestFileContentType = requestFileContentType;
	}

	public String getUpldedFileContentType() {
		return upldedFileContentType;
	}

	public void setUpldedFileContentType(String upldedFileContentType) {
		this.upldedFileContentType = upldedFileContentType;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public String getIsTemplateConfigured() {
		return isTemplateConfigured;
	}

	public void setIsTemplateConfigured(String isTemplateConfigured) {
		this.isTemplateConfigured = isTemplateConfigured;
	}

	public String getAllowMultiple() {
		return allowMultiple;
	}

	public void setAllowMultiple(String allowMultiple) {
		this.allowMultiple = allowMultiple;
	}

	public LookUpData getDocumentType() {
		return documentType;
	}

	public void setDocumentType(LookUpData documentType) {
		this.documentType = documentType;
	}

	public JSONArray getDocsHistoryList() {
		return docsHistoryList;
	}

	public void setDocsHistoryList(JSONArray docsHistoryList) {
		this.docsHistoryList = docsHistoryList;
	}

	public String getDocumentOtherType() {
		return documentOtherType;
	}

	public void setDocumentOtherType(String documentOtherType) {
		this.documentOtherType = documentOtherType;
	}

	public List<ImmigrationCandidateDocumentsHistory> getCandidateDocumentsHistories() {
		return candidateDocumentsHistories;
	}

	public void setCandidateDocumentsHistories(List<ImmigrationCandidateDocumentsHistory> candidateDocumentsHistories) {
		this.candidateDocumentsHistories = candidateDocumentsHistories;
	}

	public String getDocCategory() {
		return docCategory;
	}

	public void setDocCategory(String docCategory) {
		this.docCategory = docCategory;
	}
}
