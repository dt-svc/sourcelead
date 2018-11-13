package com.bis.sourcelead.dto;

public class ImmigrationLcaDTO{

	private String lcaNo;
	
	private String startDate;
	
	private String endDate;
	
	private String location;
	
	private String prevailingWage;
	
	private String lcaId;
	
	private String privilingWageCurrency;
	
	private String privilingWageRateType;
	
	private String status;

	public String getLcaNo() {
		return lcaNo;
	}

	public void setLcaNo(String lcaNo) {
		this.lcaNo = lcaNo;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPrevailingWage() {
		return prevailingWage;
	}

	public void setPrevailingWage(String prevailingWage) {
		this.prevailingWage = prevailingWage;
	}

	public String getLcaId() {
		return lcaId;
	}

	public void setLcaId(String lcaId) {
		this.lcaId = lcaId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}