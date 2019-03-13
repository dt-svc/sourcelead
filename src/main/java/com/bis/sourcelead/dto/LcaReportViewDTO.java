package com.bis.sourcelead.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.bis.sourcelead.dto.SearchDTO;


@SuppressWarnings("serial")
public class LcaReportViewDTO extends SearchDTO{
	
private static final long serialVersionUID = 1L;
	
	private long id;
	
	private Long orgLocationId;
    
	private String lcaNo;
	
	private String lcaTitle;
	
	private Date approvedDate;
	
	private Date startDate;
	
	private Date endDate;
	
	private String lcaType;
	
	private String available;
	
    private String socCode;
	
	private String socOccupationTitle;
	
	private Integer noOfPositions;
	
	private String jobTitle;
	
	private String wagesLevel;
	
	private String wagesRange;
	
	private String prevailingWage;
	
    private String privilingWageCurrency;
	
	private String privilingWageRateType;
	
	private String lcaStatus;
	
	private String Status;
	
	private String startDateStr;
	
	private String endDateStr;
	
	private String location;
	
	private List<String> locationsList;
	
	private String mainStatus;
	
	private String availability;
	
	private String expired;
	
	private String exportType;
	
	private String lcaUuid;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLcaNo() {
		return lcaNo;
	}

	public void setLcaNo(String lcaNo) {
		this.lcaNo = lcaNo;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLcaType() {
		return lcaType;
	}

	public void setLcaType(String lcaType) {
		this.lcaType = lcaType;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getWagesLevel() {
		return wagesLevel;
	}

	public void setWagesLevel(String wagesLevel) {
		this.wagesLevel = wagesLevel;
	}

	public String getWagesRange() {
		return wagesRange;
	}

	public void setWagesRange(String wagesRange) {
		this.wagesRange = wagesRange;
	}

	

	public String getLcaStatus() {
		return lcaStatus;
	}

	public void setLcaStatus(String lcaStatus) {
		this.lcaStatus = lcaStatus;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getLcaTitle() {
		return lcaTitle;
	}

	public void setLcaTitle(String lcaTitle) {
		this.lcaTitle = lcaTitle;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getPrevailingWage() {
		return prevailingWage;
	}

	public void setPrevailingWage(String prevailingWage) {
		this.prevailingWage = prevailingWage;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<String> getLocationsList() {
		return locationsList;
	}

	public void setLocationsList(List<String> locationsList) {
		this.locationsList = locationsList;
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

	public Integer getNoOfPositions() {
		return noOfPositions;
	}

	public void setNoOfPositions(Integer noOfPositions) {
		this.noOfPositions = noOfPositions;
	}

	public String getMainStatus() {
		return mainStatus;
	}

	public void setMainStatus(String mainStatus) {
		this.mainStatus = mainStatus;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

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

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getLcaUuid() {
		return lcaUuid;
	}

	public void setLcaUuid(String lcaUuid) {
		this.lcaUuid = lcaUuid;
	}
	
	
	
}
