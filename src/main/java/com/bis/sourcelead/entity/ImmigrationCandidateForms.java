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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "IMMIGRATION_CANDIDATE_FORMS")
public class ImmigrationCandidateForms implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IMMIGRATION_TEMPLATE_ID", nullable = false)
	private ImmigrationTemplate immigrationTempId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IMMIGRATION_ID", nullable = false)
	private Immigration immigration;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_ID")
	private Candidate candidate;
	
	@Column(name = "FORM_NAME")
	private String formName;
	
	@Column(name = "FILE_NAME")
	@Type(type="text")
	private String fileName;
	
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_URL")
	@Type(type="text")
	private String fileUrl;
	
	@Column(name = "IS_TEMPALTE_CONFIGURED")
	private String isTemplateConfigured;
	
	@Column(name = "FORM_STATUS")
	private String formStatus;
	
	@Column(name = "UPLOADED_FILE_NAME")
	@Type(type="text")
	private String uploadFileName;
	
	@Column(name = "UPLOADED_FILE_CONTENT_TYPE")
	private String uploadedFileContentType;
	
	@Column(name = "UPLOADED_FILE_URL")
	@Type(type="text")
	private String uploadedFileUrl;

	@Column(name = "FORM_UUID ")
	private String formUUID;

	@Column(name = "UPLOADED_FORM_UUID ")
	private String uploadedFormUUID;

	@Column(name = "DESCRIPTION")
	@Type(type="text")
	private String description;

	@Column(name = "FORM_TYPE")
	private String formType;

	@Column(name = "IS_REQUIRED ")
	private String isRequired;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "CREATED_DATE ")
	private Date createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;
	
	@Column(name = "FORM_CATEGORY")
	private String formCategory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ImmigrationTemplate getImmigrationTempId() {
		return immigrationTempId;
	}

	public void setImmigrationTempId(ImmigrationTemplate immigrationTempId) {
		this.immigrationTempId = immigrationTempId;
	}

	public Immigration getImmigration() {
		return immigration;
	}

	public void setImmigration(Immigration immigration) {
		this.immigration = immigration;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadedFileContentType() {
		return uploadedFileContentType;
	}

	public void setUploadedFileContentType(String uploadedFileContentType) {
		this.uploadedFileContentType = uploadedFileContentType;
	}

	public String getUploadedFileUrl() {
		return uploadedFileUrl;
	}

	public void setUploadedFileUrl(String uploadedFileUrl) {
		this.uploadedFileUrl = uploadedFileUrl;
	}

	public String getFormUUID() {
		return formUUID;
	}

	public void setFormUUID(String formUUID) {
		this.formUUID = formUUID;
	}

	public String getUploadedFormUUID() {
		return uploadedFormUUID;
	}

	public void setUploadedFormUUID(String uploadedFormUUID) {
		this.uploadedFormUUID = uploadedFormUUID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
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

	public String getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}

	public String getIsTemplateConfigured() {
		return isTemplateConfigured;
	}

	public void setIsTemplateConfigured(String isTemplateConfigured) {
		this.isTemplateConfigured = isTemplateConfigured;
	}

	public String getFormCategory() {
		return formCategory;
	}

	public void setFormCategory(String formCategory) {
		this.formCategory = formCategory;
	}
}
