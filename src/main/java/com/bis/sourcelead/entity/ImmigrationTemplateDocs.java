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
@Table(name = "IMMIGRATION_TEMPLATE_DOCUMENTS")
public class ImmigrationTemplateDocs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IMMIGRATION_TEMPLATE_ID", nullable = false)
	private ImmigrationTemplate immigrationTempId;

	@ManyToOne
	@JoinColumn(name = "DOCUMENT_TYPE")
	private LookUpData documentType;

	@Column(name = "DOCUMENT_OTHER_TYPE")
	private String documentOtherType;

	@Column(name = "DESCRIPTION")
	@Type(type="text")
	private String description;
	
	
	@Column(name = "DOCUMENT_UUID ")
	private String docUUID;

	@Column(name = "IS_REQUIRED ")
	private String isRequired;
	
	@Column(name = "IS_MULTIPLE_REQUIRED ")
	private String isMultipleRequired;

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
	
	@Column(name = "DOCUMENT_CATEGORY")
	private String documentCategory;

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

	public LookUpData getDocumentType() {
		return documentType;
	}

	public void setDocumentType(LookUpData documentType) {
		this.documentType = documentType;
	}

	public String getDocumentOtherType() {
		return documentOtherType;
	}

	public void setDocumentOtherType(String documentOtherType) {
		this.documentOtherType = documentOtherType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getIsMultipleRequired() {
		return isMultipleRequired;
	}

	public void setIsMultipleRequired(String isMultipleRequired) {
		this.isMultipleRequired = isMultipleRequired;
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

	public String getDocumentCategory() {
		return documentCategory;
	}

	public void setDocumentCategory(String documentCategory) {
		this.documentCategory = documentCategory;
	}

}
