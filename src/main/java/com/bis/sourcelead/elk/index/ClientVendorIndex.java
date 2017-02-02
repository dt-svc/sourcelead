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
	
	private String responderLevel;
	
	private String country;
	
	private String website;

	private String state;

	private String city;
	
	private String status;
	
	private Long ownerId;
	
	private String createdBy;
	
	private String searchType;
	
	

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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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


}
