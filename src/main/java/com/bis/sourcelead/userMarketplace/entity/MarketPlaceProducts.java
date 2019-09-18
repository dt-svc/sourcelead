package com.bis.sourcelead.userMarketplace.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "MARKETPLACE_PRODUCTS")
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
	  
@ManyToOne
@JoinColumn(name="ORG_LOCATION_ID")        
private OrgLocations orgLocationId;

@ManyToOne
@JoinColumn(name = "CREATED_BY")
private User createdBy;

@ManyToOne
@JoinColumn(name = "UPDATED_BY")
private User updatedBy;

@Column(name = "UUID")
private String uuid;	

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

@Column(name = "FINAL_PRICE")
private String finalPrice;

@Column(name = "MANUFACTURER_NAME")
private String manufacturerName;

@Column(name = "MANUFACTURER_DATE")
private Date manufacturerDate;

@Column(name = "IS_IN_WARRANTY")
private String isInWarranty;

@Column(name = "WARRANTY_END_DATE")
private Date warrantyEndDate;

@Column(name = "PRODUCT_CURRENT_STATUS")
private String productCurrentStatus;

@Column(name = "AVLBL_QUANTITY")
private int avlblQuantity;

@Column(name = "PRODUCT_AVLBL_END_DATE")
private Date producyAvlblEndDate;

@Column(name = "CATEGORY")
private String category;

@Column(name = "PRICE")
private String price;

@Column(name = "CURRENT_RATE_CURRENCY")
private String currentRateCurrency;

@Column(name = "STATUS")
private String status;

@Column(name="CREATED_DATE")
private Date createdDate;

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

public String getFinalPrice() {
	return finalPrice;
}

public void setFinalPrice(String finalPrice) {
	this.finalPrice = finalPrice;
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

public String getProductCurrentStatus() {
	return productCurrentStatus;
}

public void setProductCurrentStatus(String productCurrentStatus) {
	this.productCurrentStatus = productCurrentStatus;
}

public int getAvlblQuantity() {
	return avlblQuantity;
}

public void setAvlblQuantity(int avlblQuantity) {
	this.avlblQuantity = avlblQuantity;
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

public String getCurrentRateCurrency() {
	return currentRateCurrency;
}

public void setCurrentRateCurrency(String currentRateCurrency) {
	this.currentRateCurrency = currentRateCurrency;
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

public Date getProducyAvlblEndDate() {
	return producyAvlblEndDate;
}

public void setProducyAvlblEndDate(Date producyAvlblEndDate) {
	this.producyAvlblEndDate = producyAvlblEndDate;
}


}
