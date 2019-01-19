package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.entity.AssetAssign;
import com.bis.sourcelead.entity.Employees;

public class AssetDTO extends SearchDTO implements Serializable {

	    @Required(desc="sno")
		private String sno;
	    
	    private String assetId;
	    
		private String make;
		
		private String model;
		
		private String name;
		
		private String memory;
		
		private String cpu;
		
		private String harddisk;
		
		private String monitor;
		
		private Long catagoryHiddenId;
		
		@Required(desc="category")
		private String category;
		
		@Required(desc="working status")
		private String workingStatus;
		
		private String description;
		
		private String staticPath;
		
		private List<FileInfo> attachmentUpload;
		
		private String fileName;
		
		private String fileUrl;
		
		private String fileContentType;
		
		private List<Employees> employeesList;
		
		private String categoryName;
		
		private String categoryId;
		
		private String issueDate;
		
		private List<CustomFormFieldDTO> customFormData;
		
		private Map<Long,String> assetCustomFieldlabelNameAndValue;
		
		private String macAddress;
		
		private String categoryIds;
		
		private String shiftIds;
		
		private List<AssetAssignDTO> assignAsset;
		
		private List<AssetAssign> assetAssignList;
		
		private String tags;
		
		private String purchaseDate;
		
		private String depreciationDate;
		
		private String operatingSystem;
		
		private String osKey;
		
		private String antiVirus;
		
		private BigDecimal cost;
		
		private BigDecimal depreciationAmount;
		
		private String cpuSerialNumber;
		
		private String vendorDetails;
		
		private String exportType;
		
		public List<CustomFormFieldDTO> getCustomFormData() {
			return customFormData;
		}

		public void setCustomFormData(List<CustomFormFieldDTO> customFormData) {
			this.customFormData = customFormData;
		}

		public String getSno() {
			return sno;
		}

		public void setSno(String sno) {
			this.sno = sno;
		}

		public String getMake() {
			return make;
		}

		public void setMake(String make) {
			this.make = make;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMemory() {
			return memory;
		}

		public void setMemory(String memory) {
			this.memory = memory;
		}

		public String getCpu() {
			return cpu;
		}

		public void setCpu(String cpu) {
			this.cpu = cpu;
		}

		public String getHarddisk() {
			return harddisk;
		}

		public void setHarddisk(String harddisk) {
			this.harddisk = harddisk;
		}

		public String getMonitor() {
			return monitor;
		}

		public void setMonitor(String monitor) {
			this.monitor = monitor;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getWorkingStatus() {
			return workingStatus;
		}

		public void setWorkingStatus(String workingStatus) {
			this.workingStatus = workingStatus;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getStaticPath() {
			return staticPath;
		}

		public void setStaticPath(String staticPath) {
			this.staticPath = staticPath;
		}

		public List<FileInfo> getAttachmentUpload() {
			return attachmentUpload;
		}

		public void setAttachmentUpload(List<FileInfo> attachmentUpload) {
			this.attachmentUpload = attachmentUpload;
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

		public String getFileContentType() {
			return fileContentType;
		}

		public void setFileContentType(String fileContentType) {
			this.fileContentType = fileContentType;
		}

		public List<Employees> getEmployeesList() {
			return employeesList;
		}

		public void setEmployeesList(List<Employees> employeesList) {
			this.employeesList = employeesList;
		}


		public String getCategoryName() {
			return categoryName;
		}

		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}

		public String getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(String categoryId) {
			this.categoryId = categoryId;
		}


		public String getIssueDate() {
			return issueDate;
		}

		public void setIssueDate(String issueDate) {
			this.issueDate = issueDate;
		}

		public Map<Long, String> getAssetCustomFieldlabelNameAndValue() {
			return assetCustomFieldlabelNameAndValue;
		}

		public void setAssetCustomFieldlabelNameAndValue(Map<Long, String> assetCustomFieldlabelNameAndValue) {
			this.assetCustomFieldlabelNameAndValue = assetCustomFieldlabelNameAndValue;
		}

		public String getMacAddress() {
			return macAddress;
		}

		public void setMacAddress(String macAddress) {
			this.macAddress = macAddress;
		}

		public String getCategoryIds() {
			return categoryIds;
		}

		public void setCategoryIds(String categoryIds) {
			this.categoryIds = categoryIds;
		}

		public Long getCatagoryHiddenId() {
			return catagoryHiddenId;
		}

		public void setCatagoryHiddenId(Long catagoryHiddenId) {
			this.catagoryHiddenId = catagoryHiddenId;
		}

		public List<AssetAssignDTO> getAssignAsset() {
			return assignAsset;
		}

		public void setAssignAsset(List<AssetAssignDTO> assignAsset) {
			this.assignAsset = assignAsset;
		}

		public String getAssetId() {
			return assetId;
		}

		public void setAssetId(String assetId) {
			this.assetId = assetId;
		}

		public List<AssetAssign> getAssetAssignList() {
			return assetAssignList;
		}

		public void setAssetAssignList(List<AssetAssign> assetAssignList) {
			this.assetAssignList = assetAssignList;
		}

		public String getShiftIds() {
			return shiftIds;
		}

		public void setShiftIds(String shiftIds) {
			this.shiftIds = shiftIds;
		}

		public String getTags() {
			return tags;
		}

		public void setTags(String tags) {
			this.tags = tags;
		}

		public String getPurchaseDate() {
			return purchaseDate;
		}

		public String getDepreciationDate() {
			return depreciationDate;
		}

		public String getOperatingSystem() {
			return operatingSystem;
		}

		public String getOsKey() {
			return osKey;
		}

		public String getAntiVirus() {
			return antiVirus;
		}

		public BigDecimal getCost() {
			return cost;
		}

		public BigDecimal getDepreciationAmount() {
			return depreciationAmount;
		}

		public void setPurchaseDate(String purchaseDate) {
			this.purchaseDate = purchaseDate;
		}

		public void setDepreciationDate(String depreciationDate) {
			this.depreciationDate = depreciationDate;
		}

		public void setOperatingSystem(String operatingSystem) {
			this.operatingSystem = operatingSystem;
		}

		public void setOsKey(String osKey) {
			this.osKey = osKey;
		}

		public void setAntiVirus(String antiVirus) {
			this.antiVirus = antiVirus;
		}

		public void setCost(BigDecimal cost) {
			this.cost = cost;
		}

		public void setDepreciationAmount(BigDecimal depreciationAmount) {
			this.depreciationAmount = depreciationAmount;
		}

		public String getCpuSerialNumber() {
			return cpuSerialNumber;
		}

		public void setCpuSerialNumber(String cpuSerialNumber) {
			this.cpuSerialNumber = cpuSerialNumber;
		}

		public String getVendorDetails() {
			return vendorDetails;
		}

		public void setVendorDetails(String vendorDetails) {
			this.vendorDetails = vendorDetails;
		}

		public String getExportType() {
			return exportType;
		}

		public void setExportType(String exportType) {
			this.exportType = exportType;
		}
}
