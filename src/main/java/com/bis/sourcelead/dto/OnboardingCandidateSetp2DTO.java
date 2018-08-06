package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.bis.sourcelead.annotations.Required;

/**
 * @author Laxman
 *
 */
@SuppressWarnings("serial")
public class OnboardingCandidateSetp2DTO implements Serializable {
	@Required(desc="firstName")
	private String firstName;
	
	private String lastName;
	
	private String country;
	
	@Required(desc="mobileNumber")
	private String mobileNumber;
	
	private String state;
	
	private String city;
	
	@Required(desc="location")
	private String location;
	
	private String zipCode;
	
	@Required(desc="address1")
	private String address1;
	
	private String address2;
	
	private String gender;
	
    private String dateOfBirth;
	
	private String employmentEligibility;
	
	private String uscisAlien;
	
	private String authorizedWorkUntil;
	

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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getEmploymentEligibility() {
		return employmentEligibility;
	}

	public String getUscisAlien() {
		return uscisAlien;
	}

	public String getAuthorizedWorkUntil() {
		return authorizedWorkUntil;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setEmploymentEligibility(String employmentEligibility) {
		this.employmentEligibility = employmentEligibility;
	}

	public void setUscisAlien(String uscisAlien) {
		this.uscisAlien = uscisAlien;
	}

	public void setAuthorizedWorkUntil(String authorizedWorkUntil) {
		this.authorizedWorkUntil = authorizedWorkUntil;
	}
}