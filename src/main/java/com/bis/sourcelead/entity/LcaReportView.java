package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LCA_REPORT_VIEW")
public class LcaReportView implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "ORG_LOCATION_ID")
	private Long orgLocationId;

	@Column(name = "LCA_NO")
	private String lcaNo;
	
	@Column(name = "LCA_Title")
	private String lcaTitle;
	
	@Column(name = "LCA_STATUS")
	private String lcaStatus;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "JOB_TITLE")
	private String jobTitle;
	
	@Column(name = "WAGES_LEVEL")
	private String wagesLevel;
	
	@Column(name = "PREVAILING_WAGE")
	private String prevailingWage;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "LOCATION" ,columnDefinition = "TEXT default NULL")
	private String location;
	
	@Column(name = "SOC_CODE")
    private String socCode;
	
	@Column(name = "SOC_OCCUPATION_TITLE")
	private String socOccupationTitle;
	
	@Column(name = "NO_OF_POSITIONS")
	private Integer noOfPositions;
	
	@Column(name = "AVAILABLE_POSITIONS")
	private Integer availablePositions;
	
	@Column(name = "PRIVILING_WAGE_CURRENCY")
	private String privilingWageCurrency;
	
	@Column(name = "PRIVILING_WAGE_RATETYPE")
	private String privilingWageRateType;
	
	@Column(name = "LCA_UUID ")
	private String lcaUuid;
	
	@Column(name = "EXPIRED ")
	private String expired;

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getWagesLevel() {
		return wagesLevel;
	}

	public void setWagesLevel(String wagesLevel) {
		this.wagesLevel = wagesLevel;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
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

	public Integer getAvailablePositions() {
		return availablePositions;
	}

	public void setAvailablePositions(Integer availablePositions) {
		this.availablePositions = availablePositions;
	}

	public String getLcaUuid() {
		return lcaUuid;
	}

	public void setLcaUuid(String lcaUuid) {
		this.lcaUuid = lcaUuid;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

	
	
}
