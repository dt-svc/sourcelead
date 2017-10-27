package com.bis.sourcelead.dto;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.OffBoardingDocuments;
import com.bis.sourcelead.enums.OnboardStatus;

public class OffBoardingDocumentsDTO {
	
	private Long id;
	private String fileContentType;
	
	private String fileName;
	private String documentUUID;
	private OnboardStatus documentStatus;
	private Integer status;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
	private String fileURL;
	private String offboardDocumentType;
	private List<OffBoardingDocuments> resignationDocumentsList;
	private List<OffBoardingDocuments> offboardPackDocumentsList;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getFileURL() {
		return fileURL;
	}
	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
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
	
	

}
