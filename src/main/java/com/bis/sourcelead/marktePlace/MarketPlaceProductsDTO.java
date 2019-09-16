package com.bis.sourcelead.marktePlace;

import java.sql.Date;

public class MarketPlaceProductsDTO {
	
	private Long id;
	private String uuid;
	private String productCode;
	private String productName;
	private String productShareType;
	private String ShareContactDetails;
	private String productDescription;
	private String isNegotiable;
	private String finalPrice;
	private String manufacturerName;
	private Date manufacturerDate;
	private String isInWarranty;
	private Date warrantyEndDate;
	private String productCurrentStatus;
	private int avlblQuantity;
	private Date producyAvlblEndDate;
	private String category;
	private String price;
	private String currentRateCurrency;
	private String status;
	private Date createdDate;
	private Date updatedDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Date getProducyAvlblEndDate() {
		return producyAvlblEndDate;
	}
	public void setProducyAvlblEndDate(Date producyAvlblEndDate) {
		this.producyAvlblEndDate = producyAvlblEndDate;
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

}
