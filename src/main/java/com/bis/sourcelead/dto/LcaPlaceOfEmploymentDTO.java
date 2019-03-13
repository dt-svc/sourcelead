package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;

public class LcaPlaceOfEmploymentDTO extends SearchDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String plsempmntId;

	private String addressLine1;
	
	private String addressLine2;
	
	private String country;

	private String state;

	private String city;

	private String zipCode;
	
	private String status;

	private String createdDate;

	private String createdBy;

	private String updatedDate;

	private String updatedBy;
	
	@Required(desc = "location")
	private String location;
	
	private Long lcaPlacaOfempDeletedListIds[];

	public String getPlsempmntId() {
		return plsempmntId;
	}

	public void setPlsempmntId(String plsempmntId) {
		this.plsempmntId = plsempmntId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
	
}