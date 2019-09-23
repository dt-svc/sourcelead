package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "LCA_LIST_EXPORT_VIEW")
public class LcaListExportView implements Serializable {

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
	
	/*@Column(name = "EMP_NAME") // need to update later
	private char empName;
	
	@Column(name = "EMP_EMAIL")// need to update later
	private char empEmail;*/
	
	@Column(name = "NO_OF_POSITIONS")
	private Integer noOfPositions;
	
	@Column(name = "LCA_STATUS")
	private String lcaStatus;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "SOC_CODE")
	private String socCode;
	
	@Column(name = "SOC_OCCUPATION_TITLE")
	private String socOccupationTitle;
	
	
	@Column(name = "JOB_TITLE")
	private String jobTitle;
    
	@Column(name = "WAGES_LEVEL")
	private String wagesLevel;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "PREVAILING_WAGE")
	private String prevailingWage;
	
	@Column(name = "PRIVILING_WAGE_CURRENCY")
	private String privilingWageCurrency;
	
	@Column(name = "PRIVILING_WAGE_RATETYPE")
	private String privilingWageRateType;
	
    
   @Column(name = "LOCATION" ,columnDefinition = "TEXT default NULL")
    private String location;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
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

	public String getWagesLevel() {
		return wagesLevel;
	}

	public void setWagesLevel(String wagesLevel) {
		this.wagesLevel = wagesLevel;
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

	public String getPrevailingWage() {
		return prevailingWage;
	}

	public void setPrevailingWage(String prevailingWage) {
		this.prevailingWage = prevailingWage;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Integer getNoOfPositions() {
		return noOfPositions;
	}

	public void setNoOfPositions(Integer noOfPositions) {
		this.noOfPositions = noOfPositions;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	/*public char getEmpName() {
		return empName;
	}

	public void setEmpName(char empName) {
		this.empName = empName;
	}

	public char getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(char empEmail) {
		this.empEmail = empEmail;
	}
*/
	
}
