package com.bis.sourcelead.userMarketplace.entity;

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

import com.bis.sourcelead.entity.User;

@Entity
@Table(name = "MARKETPLACE_PRODUCT_FILES_HISTORY")
public class MarketPlaceProductFilesHistory {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "ID", nullable = false)
private Long id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "MARKETPLACE_PRODUCT_ID",nullable = false)
private MarketPlaceProducts marketPlaceProductId;

@ManyToOne
@JoinColumn(name = "CREATED_BY")
private User createdBy;

@ManyToOne
@JoinColumn(name = "UPDATED_BY")
private User updatedBy;

@Column(name = "UPLOADED_FILE_NAME")
private String uploadedfileName;	

@Column(name = "UPLOADED_FILE_CONTENT_TYPE")
private String upleadedFileContentType;	

@Column(name = "UPLOADED_FILE_URL")
private String uploadedFileUrl;	

@Column(name = "STATUS")
private String status;	

@Column(name = "CREATED_DATE")
private Date createdDate;	

@Column(name = "UPDATED_DATE")
private Date updatedDate;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public MarketPlaceProducts getMarketPlaceProductId() {
	return marketPlaceProductId;
}

public void setMarketPlaceProductId(MarketPlaceProducts marketPlaceProductId) {
	this.marketPlaceProductId = marketPlaceProductId;
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

public String getStatus() {
	return status;
}

public void setStatus(String status) {
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
