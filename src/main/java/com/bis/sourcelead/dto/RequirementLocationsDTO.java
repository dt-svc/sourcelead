package com.bis.sourcelead.dto;

public class RequirementLocationsDTO {

	private String locationName;
	
	private String country;
	
	private String state;
	
	private String city;
	
	private String zipcode;
	
	private String longitude;
	
	private String latitude;
	
	private Long id;
	
	private String locationUUID;

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
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

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocationUUID() {
		return locationUUID;
	}

	public void setLocationUUID(String locationUUID) {
		this.locationUUID = locationUUID;
	}
	
	
}
