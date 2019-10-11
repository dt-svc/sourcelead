package com.bis.sourcelead.userMarketplace.dto;

import java.io.Serializable;

import java.util.List;

import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.SearchDTO;

public class MarketPlaceProductsDTO extends SearchDTO implements Serializable {
	private Long id;
	private String marketPlaceId;
	private String orgLocationId;
	private String uuid;
	private String productCode;
	private String productName;
	private String productShareType;
	private String ShareContactDetails;
	private String productDescription;
	private String isNegotiable;
	private String finalPrice;
	private String manufacturerName;
	private String manufacturerDate;
	private String isInWarranty;
	private String warrantyEndDate;
	private String productCurrentStatus;
	private int avlblQuantity;
	private String comments;
	private String producyAvlblEndDate;
	private String category;
	private String price;
	private String currencySpecification;
	private String priority;
	private String currentRateCurrency;
	private String currentCity;
	private String contentType;
    private String uploadPhotos;
	private String status;
	private List<FileInfo> fileInfo;
	private List<FileInfo> uploadInfo;
	private List<MarketPlaceProductFilesHistoryDTO> filesHystoryList;
	private String docFile;
	private String staticPath;
	private String createdDate;
	private String updatedDate;
	private String fromDate;
	private String toDate;
	private String regionType;
	private boolean  iswarrantyExperyDate;
	
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
	public String getManufacturerDate() {
		return manufacturerDate;
	}
	public void setManufacturerDate(String manufacturerDate) {
		this.manufacturerDate = manufacturerDate;
	}
	public String getIsInWarranty() {
		return isInWarranty;
	}
	public void setIsInWarranty(String isInWarranty) {
		this.isInWarranty = isInWarranty;
	}
	public String getWarrantyEndDate() {
		return warrantyEndDate;
	}
	public void setWarrantyEndDate(String warrantyEndDate) {
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getProducyAvlblEndDate() {
		return producyAvlblEndDate;
	}
	public void setProducyAvlblEndDate(String producyAvlblEndDate) {
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
	public String getCurrencySpecification() {
		return currencySpecification;
	}
	public void setCurrencySpecification(String currencySpecification) {
		this.currencySpecification = currencySpecification;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getCurrentRateCurrency() {
		return currentRateCurrency;
	}
	public void setCurrentRateCurrency(String currentRateCurrency) {
		this.currentRateCurrency = currentRateCurrency;
	}
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getRegionType() {
		return regionType;
	}
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}
	public boolean isIswarrantyExperyDate() {
		return iswarrantyExperyDate;
	}
	public void setIswarrantyExperyDate(boolean iswarrantyExperyDate) {
		this.iswarrantyExperyDate = iswarrantyExperyDate;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getStaticPath() {
		return staticPath;
	}
	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}
	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}
	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}
	public String getDocFile() {
		return docFile;
	}
	public void setDocFile(String docFile) {
		this.docFile = docFile;
	}
	public String getMarketPlaceId() {
		return marketPlaceId;
	}
	public void setMarketPlaceId(String marketPlaceId) {
		this.marketPlaceId = marketPlaceId;
	}
	public String getUploadPhotos() {
		return uploadPhotos;
	}
	public void setUploadPhotos(String uploadPhotos) {
		this.uploadPhotos = uploadPhotos;
	}
	public List<FileInfo> getUploadInfo() {
		return uploadInfo;
	}
	public void setUploadInfo(List<FileInfo> uploadInfo) {
		this.uploadInfo = uploadInfo;
	}
	public List<MarketPlaceProductFilesHistoryDTO> getFilesHystoryList() {
		return filesHystoryList;
	}
	public void setFilesHystoryList(List<MarketPlaceProductFilesHistoryDTO> filesHystoryList) {
		this.filesHystoryList = filesHystoryList;
	}
	public String getOrgLocationId() {
		return orgLocationId;
	}
	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
	
}
