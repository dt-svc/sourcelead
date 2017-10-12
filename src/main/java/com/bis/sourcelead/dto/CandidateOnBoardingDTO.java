package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("serial")
public class CandidateOnBoardingDTO implements Serializable {
	
	private String savedonboardId;
	
	private String onboardCandiate;
	
	private String firstName;
	
	private String lastName;
	
	private String address1;
	
	private String address2;
	
	private String location;
	
	private String country;
	
	private String state;
	
	private String city;
	
	private String zipCode;
	
	private String mobileNumber;
	
	private String personalEmail;
	
	private String companyEmail;
	
	private String onboardCandidateRole;
	
	private String startDate;
	
	private String endDate;
	
	private String salaryRate;
	
	private String paymentType;
	
	private String paymentMode;
	
	private String reportingManager;
	
	private String team;
	
	private String employmentType;
	
	private String employmentOtherType;
	
	private String probationNotice;
	
	private String terminationNotice;

	private String dueDate;
	
	private String specialConditions;
	
	private String onboardingTemplate;

	
	public String getSavedonboardId() {
		return savedonboardId;
	}

	public void setSavedonboardId(String savedonboardId) {
		this.savedonboardId = savedonboardId;
	}

	public String getOnboardCandiate() {
		return onboardCandiate;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getLocation() {
		return location;
	}

	public String getCountry() {
		return country;
	}

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getPersonalEmail() {
		return personalEmail;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public String getOnboardCandidateRole() {
		return onboardCandidateRole;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getSalaryRate() {
		return salaryRate;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public String getReportingManager() {
		return reportingManager;
	}

	public String getTeam() {
		return team;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public String getEmploymentOtherType() {
		return employmentOtherType;
	}

	public String getProbationNotice() {
		return probationNotice;
	}

	public String getTerminationNotice() {
		return terminationNotice;
	}

	public String getDueDate() {
		return dueDate;
	}

	public String getSpecialConditions() {
		return specialConditions;
	}

	 
	public void setOnboardCandiate(String onboardCandiate) {
		this.onboardCandiate = onboardCandiate;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public void setOnboardCandidateRole(String onboardCandidateRole) {
		this.onboardCandidateRole = onboardCandidateRole;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setSalaryRate(String salaryRate) {
		this.salaryRate = salaryRate;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public void setEmploymentOtherType(String employmentOtherType) {
		this.employmentOtherType = employmentOtherType;
	}

	public void setProbationNotice(String probationNotice) {
		this.probationNotice = probationNotice;
	}

	public void setTerminationNotice(String terminationNotice) {
		this.terminationNotice = terminationNotice;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public void setSpecialConditions(String specialConditions) {
		this.specialConditions = specialConditions;
	}

	public String getOnboardingTemplate() {
		return onboardingTemplate;
	}

	public void setOnboardingTemplate(String onboardingTemplate) {
		this.onboardingTemplate = onboardingTemplate;
	}
}
