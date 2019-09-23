package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "LCA")
public class Lca implements Serializable {

	private static final long serialVersionUID = 1L;
   // Fields for  First Section OF LCA
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID")
	private OrgLocations orgLocationId;
	
	@Column(name = "LCA_NO")
	private String lcaNo;
	
	@Column(name = "LCA_Title")
	private String lcaTitle;
	
	@Column(name = "LCA_STATUS")
	private String lcaStatus;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "LCA_UUID ")
	private String lcaUuid;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "JOB_TITLE")
	private String jobTitle;
	
	@Column(name = "SOC_CODE")
	private String socCode;
	
	@Column(name = "SOC_OCCUPATION_TITLE")
	private String socOccupationTitle;
	
	@Column(name = "OFFERED_WAGE")
	private String offeredWage;
	
	@Column(name = "OFFERED_WAGE_CURRENCY")
	private String offeredWageCurrency;
	
	@Column(name = "OFFERED_WAGE_RATETYPE")
	private String offeredWageRateType;
	
	@Column(name = "NO_OF_POSITIONS")
	private Integer noOfPositions;
	
	@Column(name = "AVAILABLE_POSITIONS")
	private Integer availablePositions;
	
	
	@Column(name = "WAGES_LEVEL")
	private String wagesLevel;
	
	@Column(name = "PREVAILING_WAGE")
	private String prevailingWage;
	
	@Column(name = "PRIVILING_WAGE_CURRENCY")
	private String privilingWageCurrency;
	
	@Column(name = "PRIVILING_WAGE_RATETYPE")
	private String privilingWageRateType;
	
	@Column(name = "EMPLOYEE")
	private String employee;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="lcaId",orphanRemoval=true)
	private List<LcaVisaClarifications> lcaVisaClarifications;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="lcaId",orphanRemoval=true)
	private List<LcaPlaceOfEployment> lcaPlaceOfEployment;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="lcaId",orphanRemoval=true)
	private List<LcaDocumentHistory> lcaDocumentHistory;
	
	
	@Transient
	@JsonProperty
	private List<String> locationsList;
	
	@Transient
	@JsonProperty
	private String lcaLocation;
	
	@Transient
	@JsonProperty
	private String lcaStartDateStr;
	
	@Transient
	@JsonProperty
	private String lcaEndDateStr;

	public List<String> getLocationsList() {
		return locationsList;
	}

	public void setLocationsList(List<String> locationsList) {
		this.locationsList = locationsList;
	}

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


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public List<LcaVisaClarifications> getLcaVisaClarifications() {
		return lcaVisaClarifications;
	}

	public void setLcaVisaClarifications(List<LcaVisaClarifications> lcaVisaClarifications) {
		this.lcaVisaClarifications = lcaVisaClarifications;
	}

	public List<LcaPlaceOfEployment> getLcaPlaceOfEployment() {
		return lcaPlaceOfEployment;
	}

	public void setLcaPlaceOfEployment(List<LcaPlaceOfEployment> lcaPlaceOfEployment) {
		this.lcaPlaceOfEployment = lcaPlaceOfEployment;
	}

	public List<LcaDocumentHistory> getLcaDocumentHistory() {
		return lcaDocumentHistory;
	}

	public void setLcaDocumentHistory(List<LcaDocumentHistory> lcaDocumentHistory) {
		this.lcaDocumentHistory = lcaDocumentHistory;
	}

	public String getLcaUuid() {
		return lcaUuid;
	}

	public void setLcaUuid(String lcaUuid) {
		this.lcaUuid = lcaUuid;
	}

	public Integer getNoOfPositions() {
		return noOfPositions;
	}

	public void setNoOfPositions(Integer noOfPositions) {
		this.noOfPositions = noOfPositions;
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

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getLcaLocation() {
		return lcaLocation;
	}

	public void setLcaLocation(String lcaLocation) {
		this.lcaLocation = lcaLocation;
	}

	public String getLcaStartDateStr() {
		return lcaStartDateStr;
	}

	public void setLcaStartDateStr(String lcaStartDateStr) {
		this.lcaStartDateStr = lcaStartDateStr;
	}

	public String getLcaEndDateStr() {
		return lcaEndDateStr;
	}

	public void setLcaEndDateStr(String lcaEndDateStr) {
		this.lcaEndDateStr = lcaEndDateStr;
	}

	public Integer getAvailablePositions() {
		return availablePositions;
	}

	public void setAvailablePositions(Integer availablePositions) {
		this.availablePositions = availablePositions;
	}
	
	
}
