package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class LcaDTO extends SearchDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String orgloationId;
	
	@Required(desc = "lcaNo")
	private String lcaNo;
	
	@Required(desc = "lcaTitle")
	private String lcaTitle;
	
	@Required(desc = "lcaStatus")
	private String lcaStatus;
	
	@Required(desc = "status")
	private String status;
	
	private String lcaUuid;
	
	@Required(desc = "startDate")
	private String startDate;
	
	@Required(desc = "endDate")
	private String endDate;
	
	@Required(desc = "wagesLevel")
	private String wagesLevel;
	
	private String prevailingWage;
	
	private String employee;
	
	private String lcaId;
	
	private String noOfPositions;
	
	private String availablePositions;
	
	/*@Required(desc = "location")*/
	private String location;
	
	private String privilingWageCurrency;
	
	private String privilingWageRateType;
	
	//Start visa clarification dto fields
	
    private String newEmployment;
	
	private String concurrentEmployment;
	
	private String extention;
	
	private String changeInEmployer;
	
	private String changeInPreApproEmployment;
	
	private String amendment;
	
    private String newEmploymentCount;
	
	private String concurrentEmploymentCount;

	private String extentionCount;
	
	private String changeInEmployerCount;
	
	private String changeInPreApproEmplmtCount;
	
	private String amendmentCount;
	
	@Required(desc = "jobTitle")
	private String jobTitle;
	
	@Required(desc = "socCode")
	private String socCode;
	
	@Required(desc = "socOccupationTitle")
	private String socOccupationTitle;
	
	private String offeredWage;
	
	private String offeredWageCurrency;
		
	private String offeredWageRateType;
	
	//End visa clarification dto fields
	
	//Start Place of Employment dto fields
	
    private String addressLine1;
	
	private String addressLine2;
	
	private String country;

	private String state;

	private String city;

	private String zipCode;
	
	private List<LcaPlaceOfEmploymentDTO> lcaPlaceOfEmploymentList;
	
	private List<LcaDocumentHistoryDTO> lcaDocumentList;
	
	private Long lcaPlacaOfempDeletedListIds[];
	
	private Long lcaDocumentListIds[];
	
	//End Place of Employment dto fields
	
	//Start  of Docs dto fields 
    private String documentTitle;
	
	private String fileContentType;
	
	private String fileName;
	
	private String documentUUID;
	
	private String description ;
	
	private String createdDate;
	
	private String createdBy;

	private String updatedDate;

	private String updatedBy;
	
	private List<FileInfo> fileInfo;
	
	private String editMode;
	
	private String visaClsfId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLcaNo() {
		return lcaNo;
	}

	public void setLcaNo(String lcaNo) {
		this.lcaNo = lcaNo;
	}

	public String getLcaTitle() {
		return lcaTitle;
	}

	public void setLcaTitle(String lcaTitle) {
		this.lcaTitle = lcaTitle;
	}

	public String getLcaStatus() {
		return lcaStatus;
	}

	public void setLcaStatus(String lcaStatus) {
		this.lcaStatus = lcaStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLcaUuid() {
		return lcaUuid;
	}

	public void setLcaUuid(String lcaUuid) {
		this.lcaUuid = lcaUuid;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	

	public String getWagesLevel() {
		return wagesLevel;
	}

	public void setWagesLevel(String wagesLevel) {
		this.wagesLevel = wagesLevel;
	}

	public String getPrevailingWage() {
		return prevailingWage;
	}

	public void setPrevailingWage(String prevailingWage) {
		this.prevailingWage = prevailingWage;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getNewEmployment() {
		return newEmployment;
	}

	public void setNewEmployment(String newEmployment) {
		this.newEmployment = newEmployment;
	}

	public String getConcurrentEmployment() {
		return concurrentEmployment;
	}

	public void setConcurrentEmployment(String concurrentEmployment) {
		this.concurrentEmployment = concurrentEmployment;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public String getChangeInEmployer() {
		return changeInEmployer;
	}

	public void setChangeInEmployer(String changeInEmployer) {
		this.changeInEmployer = changeInEmployer;
	}

	public String getChangeInPreApproEmployment() {
		return changeInPreApproEmployment;
	}

	public void setChangeInPreApproEmployment(String changeInPreApproEmployment) {
		this.changeInPreApproEmployment = changeInPreApproEmployment;
	}

	public String getAmendment() {
		return amendment;
	}

	public void setAmendment(String amendment) {
		this.amendment = amendment;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getSocCode() {
		return socCode;
	}

	public void setSocCode(String socCode) {
		this.socCode = socCode;
	}

	public String getSocOccupationTitle() {
		return socOccupationTitle;
	}

	public void setSocOccupationTitle(String socOccupationTitle) {
		this.socOccupationTitle = socOccupationTitle;
	}

	public String getOfferedWage() {
		return offeredWage;
	}

	public void setOfferedWage(String offeredWage) {
		this.offeredWage = offeredWage;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getLcaId() {
		return lcaId;
	}

	public void setLcaId(String lcaId) {
		this.lcaId = lcaId;
	}

	public String getNoOfPositions() {
		return noOfPositions;
	}

	public void setNoOfPositions(String noOfPositions) {
		this.noOfPositions = noOfPositions;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public List<LcaPlaceOfEmploymentDTO> getLcaPlaceOfEmploymentList() {
		return lcaPlaceOfEmploymentList;
	}

	public void setLcaPlaceOfEmploymentList(List<LcaPlaceOfEmploymentDTO> lcaPlaceOfEmploymentList) {
		this.lcaPlaceOfEmploymentList = lcaPlaceOfEmploymentList;
	}

	/*public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}*/

	public String getPrivilingWageCurrency() {
		return privilingWageCurrency;
	}

	public void setPrivilingWageCurrency(String privilingWageCurrency) {
		this.privilingWageCurrency = privilingWageCurrency;
	}

	public String getPrivilingWageRateType() {
		return privilingWageRateType;
	}

	public void setPrivilingWageRateType(String privilingWageRateType) {
		this.privilingWageRateType = privilingWageRateType;
	}

	public String getOfferedWageCurrency() {
		return offeredWageCurrency;
	}

	public void setOfferedWageCurrency(String offeredWageCurrency) {
		this.offeredWageCurrency = offeredWageCurrency;
	}

	public String getOfferedWageRateType() {
		return offeredWageRateType;
	}

	public void setOfferedWageRateType(String offeredWageRateType) {
		this.offeredWageRateType = offeredWageRateType;
	}

	public String getNewEmploymentCount() {
		return newEmploymentCount;
	}

	public void setNewEmploymentCount(String newEmploymentCount) {
		this.newEmploymentCount = newEmploymentCount;
	}

	public String getConcurrentEmploymentCount() {
		return concurrentEmploymentCount;
	}

	public void setConcurrentEmploymentCount(String concurrentEmploymentCount) {
		this.concurrentEmploymentCount = concurrentEmploymentCount;
	}

	public String getExtentionCount() {
		return extentionCount;
	}

	public void setExtentionCount(String extentionCount) {
		this.extentionCount = extentionCount;
	}

	public String getChangeInEmployerCount() {
		return changeInEmployerCount;
	}

	public void setChangeInEmployerCount(String changeInEmployerCount) {
		this.changeInEmployerCount = changeInEmployerCount;
	}

	public String getChangeInPreApproEmplmtCount() {
		return changeInPreApproEmplmtCount;
	}

	public void setChangeInPreApproEmplmtCount(String changeInPreApproEmplmtCount) {
		this.changeInPreApproEmplmtCount = changeInPreApproEmplmtCount;
	}

	public String getAmendmentCount() {
		return amendmentCount;
	}

	public void setAmendmentCount(String amendmentCount) {
		this.amendmentCount = amendmentCount;
	}

	public List<LcaDocumentHistoryDTO> getLcaDocumentList() {
		return lcaDocumentList;
	}

	public void setLcaDocumentList(List<LcaDocumentHistoryDTO> lcaDocumentList) {
		this.lcaDocumentList = lcaDocumentList;
	}

	public String getOrgloationId() {
		return orgloationId;
	}

	public void setOrgloationId(String orgloationId) {
		this.orgloationId = orgloationId;
	}

	public String getEditMode() {
		return editMode;
	}

	public void setEditMode(String editMode) {
		this.editMode = editMode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long[] getLcaPlacaOfempDeletedListIds() {
		return lcaPlacaOfempDeletedListIds;
	}

	public void setLcaPlacaOfempDeletedListIds(Long[] lcaPlacaOfempDeletedListIds) {
		this.lcaPlacaOfempDeletedListIds = lcaPlacaOfempDeletedListIds;
	}

	public Long[] getLcaDocumentListIds() {
		return lcaDocumentListIds;
	}

	public void setLcaDocumentListIds(Long[] lcaDocumentListIds) {
		this.lcaDocumentListIds = lcaDocumentListIds;
	}

	public String getVisaClsfId() {
		return visaClsfId;
	}

	public void setVisaClsfId(String visaClsfId) {
		this.visaClsfId = visaClsfId;
	}

	public String getAvailablePositions() {
		return availablePositions;
	}

	public void setAvailablePositions(String availablePositions) {
		this.availablePositions = availablePositions;
	}
	
}