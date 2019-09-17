package com.bis.sourcelead.userMarketplace.dto;

import java.sql.Date;

public class MarketPlaceProductFilesHistoryDTO {
	
	private Long id;
	private String uploadedfileName;
	private String upleadedFileContentType;
	private String uploadedFileUrl;
	private int status;
	private Date createdDate;
	private Date updatedDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUploadedfileName() {
		return uploadedfileName;
	}
	public void setUploadedfileName(String uploadedfileName) {
		this.uploadedfileName = uploadedfileName;
	}
	public String getUpleadedFileContentType() {
		return upleadedFileContentType;
	}
	public void setUpleadedFileContentType(String upleadedFileContentType) {
		this.upleadedFileContentType = upleadedFileContentType;
	}
	public String getUploadedFileUrl() {
		return uploadedFileUrl;
	}
	public void setUploadedFileUrl(String uploadedFileUrl) {
		this.uploadedFileUrl = uploadedFileUrl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
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
	
}
