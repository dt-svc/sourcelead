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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "MARKETPLACE_PRODUCT")
public class MarketPlaceProducts {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "ID", nullable = false)
private Long id;

@ManyToOne    
@JoinColumn(name="ASSOCIATE_ID")        
private User userId;

@ManyToOne
@JoinColumn(name = "ORG_ID")
private Org orgId;
	  
@ManyToOne(fetch = FetchType.LAZY)
@Fetch(FetchMode.SELECT)
@JoinColumn(name = "ORG_LOCATION_ID")
private OrgLocations orgLocationId;

@ManyToOne
@JoinColumn(name = "CREATED_BY")
private User createdBy;

@ManyToOne
@JoinColumn(name = "UPDATED_BY")
private User updatedBy;

@Column(name = "UUID")
private String uuid;	

@Column(name = "File_URL")
private String fileUrl;

@Column(name = "FILE_CONTENT_TYPE")
private String fileContentType;

@Column(name = "FILE_NAME")
private String fileName;

@Column(name = "PRODUCT_CODE")
private String productCode;

@Column(name = "PRODUCT_NAME")
private String productName;

@Column(name = "PRODUCT_SHARE_TYPE")
private String productShareType;
	
@Column(name = "SHARE_CONTACT_DETAILS")
private String ShareContactDetails;

@Column(name = "PRODUCT_DESCRIPTION")
private String productDescription;

@Column(name = "IS_NEGOTIABLE")
private String isNegotiable;

@Column(name = "MANUFACTURER_NAME")
private String manufacturerName;

@Column(name = "MANUFACTURER_DATE")
private Date manufacturerDate;

@Column(name = "IS_IN_WARRANTY")
private String isInWarranty;

@Column(name = "WARRANTY_END_DATE")
private Date warrantyEndDate;

@Column(name = "CATEGORY")
private String category;

@Column(name = "PRICE")
private String price;

@Column(name = "CURRENCY_SPECIFICATION")
private String currencySpecification;

@Column(name = "COMMENTS")
private String comments;

@Column(name = "PRIORITY")
private String priority;

@Column(name = "STATUS")
private String status;

@Column(name = "REGION_TYPE")
private String regionType;

@Column(name = "CURRENT_CITY")
private String currentCity;

@Column(name="CREATED_DATE")
private Date createdDate;

@Column(name="RELOCATE_FROM_DATE")
private Date fromDate;

@Column(name="RELOCATE_TO_DATE")
private Date toDate;

@Column(name = "UPDATED_DATE")
private Date updatedDate;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public User getUserId() {
	return userId;
}

public void setUserId(User userId) {
	this.userId = userId;
}

public Org getOrgId() {
	return orgId;
}

public void setOrgId(Org orgId) {
	this.orgId = orgId;
}

public OrgLocations getOrgLocationId() {
	return orgLocationId;
}

public void setOrgLocationId(OrgLocations orgLocationId) {
	this.orgLocationId = orgLocationId;
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

public String getUuid() {
	return uuid;
}

public void setUuid(String uuid) {
	this.uuid = uuid;
}

public String getProductCode() {
	return productCode;
}

public void setProductCode(String productCode) {
	this.productCode = productCode;
}

public String getProductName() {
	return productName;
}

public void setProductName(String productName) {
	this.productName = productName;
}

public String getProductShareType() {
	return productShareType;
}

public void setProductShareType(String productShareType) {
	this.productShareType = productShareType;
}

public String getShareContactDetails() {
	return ShareContactDetails;
}

public void setShareContactDetails(String shareContactDetails) {
	ShareContactDetails = shareContactDetails;
}

public String getProductDescription() {
	return productDescription;
}

public void setProductDescription(String productDescription) {
	this.productDescription = productDescription;
}

public String getIsNegotiable() {
	return isNegotiable;
}

public void setIsNegotiable(String isNegotiable) {
	this.isNegotiable = isNegotiable;
}



public String getManufacturerName() {
	return manufacturerName;
}

public void setManufacturerName(String manufacturerName) {
	this.manufacturerName = manufacturerName;
}

public Date getManufacturerDate() {
	return manufacturerDate;
}

public void setManufacturerDate(Date manufacturerDate) {
	this.manufacturerDate = manufacturerDate;
}

public String getIsInWarranty() {
	return isInWarranty;
}

public void setIsInWarranty(String isInWarranty) {
	this.isInWarranty = isInWarranty;
}

public Date getWarrantyEndDate() {
	return warrantyEndDate;
}

public void setWarrantyEndDate(Date warrantyEndDate) {
	this.warrantyEndDate = warrantyEndDate;
}




public String getCategory() {
	return category;
}

public void setCategory(String category) {
	this.category = category;
}

public String getPrice() {
	return price;
}

public void setPrice(String price) {
	this.price = price;
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

public String getComments() {
	return comments;
}

public void setComments(String comments) {
	this.comments = comments;
}

public String getCurrentCity() {
	return currentCity;
}

public void setCurrentCity(String currentCity) {
	this.currentCity = currentCity;
}

public String getPriority() {
	return priority;
}

public void setPriority(String priority) {
	this.priority = priority;
}

public Date getFromDate() {
	return fromDate;
}

public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
}

public Date getToDate() {
	return toDate;
}

public void setToDate(Date toDate) {
	this.toDate = toDate;
}

public String getFileUrl() {
	return fileUrl;
}

public void setFileUrl(String fileUrl) {
	this.fileUrl = fileUrl;
}

public String getCurrencySpecification() {
	return currencySpecification;
}

public void setCurrencySpecification(String currencySpecification) {
	this.currencySpecification = currencySpecification;
}

public String getRegionType() {
	return regionType;
}

public void setRegionType(String regionType) {
	this.regionType = regionType;
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


}
