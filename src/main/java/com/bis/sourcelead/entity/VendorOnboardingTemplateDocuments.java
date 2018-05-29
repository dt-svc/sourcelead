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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "VENDOR_ON_BOARDING_TEMPLATE_DOCUMENTS")
public class VendorOnboardingTemplateDocuments implements Serializable{

	 private static final long serialVersionUID = 1L;
	 
     @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "ID", nullable = false)
     private Long id;
     
     
     @ManyToOne(fetch = FetchType.LAZY)
 	 @JoinColumn(name = "VENDOR_ON_BOARDING_TEMPLATE_ID", nullable = false)
 	 private VendorOnboardingTemplate vendorOnboardingTemplateId;
     
     
     @ManyToOne(fetch = FetchType.LAZY)
 	 @JoinColumn(name = "VENDOR_ON_BOARDING_ID", nullable = false)
 	 private VendorOnboarding vendorOnboardingId;
     
     @Column(name = "DOCUMENT_NAME")
 	 private String documentName;
     
     @Column(name = "DOCUMENT_TYPE")
 	 private String documentType;
     
     @Column(name = "FILE_CONTENT_TYPE")
 	 private String fileContentType;
     
     @Column(name = "FILE_NAME")
     @Type(type="text")
 	 private String fileName;
     
     @Column(name = "FILE_URL")
     @Type(type="text")
 	 private String fileUrl;
     
     @Column(name = "UPLOADED_FILE_CONTENT_TYPE")
 	 private String uploadedFileContentType;
     
     @Column(name = "UPLOADED_FILE_NAME")
     @Type(type="text")
 	 private String uploadedFileName;
     
     @Column(name = "UPLOADED_FILE_URL")
     @Type(type="text")
 	 private String uploadedFileUrl;
     
     @Column(name = "DOCUMENT_UUID")
 	 private String documentUUID;
     
     @Column(name = "DOCUMENT_STATUS")
 	 private String documentStatus;
     
     @Column(name = "IS_REQUIRED")
 	 private String isRequired;
     
     @Column(name = "STATUS")
 	 private Integer status;
 		
 	 @Column(name = "CREATED_DATE")
 	 private Date createdDate;
 	
 	 @Column(name = "UPDATED_DATE")
 	 private Date updatedDate;
 	
 	 @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "CREATED_BY")
   	 private User createdBy;
 	
 	 @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "UPDATED_BY")
   	 private User updatedBy;
 	 
 	 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VendorOnboardingTemplate getVendorOnboardingTemplateId() {
		return vendorOnboardingTemplateId;
	}

	public void setVendorOnboardingTemplateId(VendorOnboardingTemplate vendorOnboardingTemplateId) {
		this.vendorOnboardingTemplateId = vendorOnboardingTemplateId;
	}

	public VendorOnboarding getVendorOnboardingId() {
		return vendorOnboardingId;
	}

	public void setVendorOnboardingId(VendorOnboarding vendorOnboardingId) {
		this.vendorOnboardingId = vendorOnboardingId;
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

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getUploadedFileContentType() {
		return uploadedFileContentType;
	}

	public void setUploadedFileContentType(String uploadedFileContentType) {
		this.uploadedFileContentType = uploadedFileContentType;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}

	public String getUploadedFileUrl() {
		return uploadedFileUrl;
	}

	public void setUploadedFileUrl(String uploadedFileUrl) {
		this.uploadedFileUrl = uploadedFileUrl;
	}

	public String getDocumentUUID() {
		return documentUUID;
	}

	public void setDocumentUUID(String documentUUID) {
		this.documentUUID = documentUUID;
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

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
}
