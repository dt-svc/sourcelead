package com.bis.sourcelead.elk.index;

import java.io.Serializable;

public class ClientVendorIndex implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String orgName;
	
	private Long orgRelationId;
	
    private Long responderLocationId;
		
    private Long orgLocationId;
	    
	private String initiatorType;
	
	private String responderType;
	
	private String initiatorLevel;
	
	private String responderLevel ;
	
	private String initiatorCountry;
	
	private String initiatorState;

	private String initiatorCity;
	
    private String initiatorWebSite;
	
	private String initiatorOrgName;
	
    private String responderCountry;
	
	private String responderState;

	private String responderCity;
	
	private String status;
	
	private Long ownerId;
	
	private String createdBy;
	
	private String searchType;
	
	private String createdByUserCode;
	
	private String responderWebSite;
	
	private String responderOrgName;
	
    private String state;
	
	private String website;
	
	private String city;
	
	private String country;
	
	private String location;
	
	private String zipCode;
	
	private String longitude;
	
	private String latitude;
	
	private String updatedDate;
	
	private String createdDate;

	private String updateDateToString;
	
	private Long noContacts;
	
	private Long noOfReleasedJobs;
	
	private String releasedJobsCount;
	
    private String vendorOnboardTemplateId;
	
	private String vendorRelationStatus;
	
	private String orgRelationUUID;

	private String isMSPVendor;
	
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Long getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(Long orgRelationId) {
		this.orgRelationId = orgRelationId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getResponderType() {
		return responderType;
	}

	public void setResponderType(String responderType) {
		this.responderType = responderType;
	}

	public String getInitiatorLevel() {
		return initiatorLevel;
	}

	public void setInitiatorLevel(String initiatorLevel) {
		this.initiatorLevel = initiatorLevel;
	}

	public String getResponderLevel() {
		return responderLevel;
	}

	public void setResponderLevel(String responderLevel) {
		this.responderLevel = responderLevel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getInitiatorType() {
		return initiatorType;
	}

	public void setInitiatorType(String initiatorType) {
		this.initiatorType = initiatorType;
	}

	public Long getResponderLocationId() {
		return responderLocationId;
	}

	public void setResponderLocationId(Long responderLocationId) {
		this.responderLocationId = responderLocationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByUserCode() {
		return createdByUserCode;
	}

	public void setCreatedByUserCode(String createdByUserCode) {
		this.createdByUserCode = createdByUserCode;
	}

	public String getInitiatorCountry() {
		return initiatorCountry;
	}

	public void setInitiatorCountry(String initiatorCountry) {
		this.initiatorCountry = initiatorCountry;
	}

	public String getInitiatorState() {
		return initiatorState;
	}

	public void setInitiatorState(String initiatorState) {
		this.initiatorState = initiatorState;
	}

	public String getInitiatorCity() {
		return initiatorCity;
	}

	public void setInitiatorCity(String initiatorCity) {
		this.initiatorCity = initiatorCity;
	}

	public String getInitiatorWebSite() {
		return initiatorWebSite;
	}

	public void setInitiatorWebSite(String initiatorWebSite) {
		this.initiatorWebSite = initiatorWebSite;
	}

	public String getInitiatorOrgName() {
		return initiatorOrgName;
	}

	public void setInitiatorOrgName(String initiatorOrgName) {
		this.initiatorOrgName = initiatorOrgName;
	}

	public String getResponderCountry() {
		return responderCountry;
	}

	public void setResponderCountry(String responderCountry) {
		this.responderCountry = responderCountry;
	}

	public String getResponderState() {
		return responderState;
	}

	public void setResponderState(String responderState) {
		this.responderState = responderState;
	}

	public String getResponderCity() {
		return responderCity;
	}

	public void setResponderCity(String responderCity) {
		this.responderCity = responderCity;
	}

	public String getResponderWebSite() {
		return responderWebSite;
	}

	public void setResponderWebSite(String responderWebSite) {
		this.responderWebSite = responderWebSite;
	}

	public String getResponderOrgName() {
		return responderOrgName;
	}

	public void setResponderOrgName(String responderOrgName) {
		this.responderOrgName = responderOrgName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getUpdateDateToString() {
		return updateDateToString;
	}

	public void setUpdateDateToString(String updateDateToString) {
		this.updateDateToString = updateDateToString;
	}

	public Long getNoContacts() {
		return noContacts;
	}

	public void setNoContacts(Long noContacts) {
		this.noContacts = noContacts;
	}

	public Long getNoOfReleasedJobs() {
		return noOfReleasedJobs;
	}

	public void setNoOfReleasedJobs(Long noOfReleasedJobs) {
		this.noOfReleasedJobs = noOfReleasedJobs;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getReleasedJobsCount() {
		return releasedJobsCount;
	}

	public void setReleasedJobsCount(String releasedJobsCount) {
		this.releasedJobsCount = releasedJobsCount;
	}

	public String getVendorOnboardTemplateId() {
		return vendorOnboardTemplateId;
	}

	public void setVendorOnboardTemplateId(String vendorOnboardTemplateId) {
		this.vendorOnboardTemplateId = vendorOnboardTemplateId;
	}

	public String getVendorRelationStatus() {
		return vendorRelationStatus;
	}

	public void setVendorRelationStatus(String vendorRelationStatus) {
		this.vendorRelationStatus = vendorRelationStatus;
	}

	public String getOrgRelationUUID() {
		return orgRelationUUID;
	}

	public void setOrgRelationUUID(String orgRelationUUID) {
		this.orgRelationUUID = orgRelationUUID;
	}

	public String getIsMSPVendor() {
		return isMSPVendor;
	}

	public void setIsMSPVendor(String isMSPVendor) {
		this.isMSPVendor = isMSPVendor;
	}
}
