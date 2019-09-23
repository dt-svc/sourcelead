package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class LcaListViewDTO extends SearchDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private Long orgLocationId;
    
	private String lcaNo;
	
	private String lcaTitle;
	
	private Date approvedDate;
	
	private Date startDate;
	
	private Date endDate;
	
	private String lcaType;
	
	private int total;
	
	private String available;
	
	private String jobTitle;
	
	private String wagesLevel;
	
	private String wagesRange;
	
	private String prevailingWage;
	
	private String typeStandard;
	
	private String lcaStatus;
	
	private Integer Status;
	
	private String startDateStr;
	
	private String endDateStr;
	
	private String location;
	
	private String lcaUuid;
	
	private String mainStatus;
	
	private List<String> locationsList;

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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
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

	public String getTypeStandard() {
		return typeStandard;
	}

	public void setTypeStandard(String typeStandard) {
		this.typeStandard = typeStandard;
	}

	public String getLcaStatus() {
		return lcaStatus;
	}

	public void setLcaStatus(String lcaStatus) {
		this.lcaStatus = lcaStatus;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
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

	public String getLcaUuid() {
		return lcaUuid;
	}

	public void setLcaUuid(String lcaUuid) {
		this.lcaUuid = lcaUuid;
	}

	public String getMainStatus() {
		return mainStatus;
	}

	public void setMainStatus(String mainStatus) {
		this.mainStatus = mainStatus;
	}

	public List<String> getLocationsList() {
		return locationsList;
	}

	public void setLocationsList(List<String> locationsList) {
		this.locationsList = locationsList;
	}
	
}
