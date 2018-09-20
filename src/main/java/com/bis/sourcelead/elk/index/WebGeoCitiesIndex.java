package com.bis.sourcelead.elk.index;

public class WebGeoCitiesIndex {
	private Long id;
	private String cityName;
	private String countryCode;
	private String stateCode;
	private String cityNameAscii;
	private Float latitude;
	private Float longitude;
	private String timezoneid;
	private String location;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getCityNameAscii() {
		return cityNameAscii;
	}
	public void setCityNameAscii(String cityNameAscii) {
		this.cityNameAscii = cityNameAscii;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public String getTimezoneid() {
		return timezoneid;
	}
	public void setTimezoneid(String timezoneid) {
		this.timezoneid = timezoneid;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
