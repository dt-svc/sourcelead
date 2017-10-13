package com.bis.sourcelead.dto;

import java.io.Serializable;

/**
 * @author Laxman
 *
 */
@SuppressWarnings("serial")
public class OnboardingCandidateSetp2DTO implements Serializable {
	
	private String firstName;
	
	private String lastName;
	
	private String country;
	
	private String mobileNumber;
	
	private String state;
	
	private String city;
	
	private String location;
	
	private String zipCode;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getCountry() {
		return country;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public String getLocation() {
		return location;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
}