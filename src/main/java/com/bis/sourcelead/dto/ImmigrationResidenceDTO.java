package com.bis.sourcelead.dto;


public class ImmigrationResidenceDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String country;

	private String state;

	private String city;

	private String address;

	private String location;

	private String zipcode;

	private String residenceStartDate;

	private String residenceEndDate;
	
	private String isCurrResidence;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getResidenceStartDate() {
		return residenceStartDate;
	}

	public void setResidenceStartDate(String residenceStartDate) {
		this.residenceStartDate = residenceStartDate;
	}

	public String getResidenceEndDate() {
		return residenceEndDate;
	}

	public void setResidenceEndDate(String residenceEndDate) {
		this.residenceEndDate = residenceEndDate;
	}

	public String getIsCurrResidence() {
		return isCurrResidence;
	}

	public void setIsCurrResidence(String isCurrResidence) {
		this.isCurrResidence = isCurrResidence;
	}
	
}
