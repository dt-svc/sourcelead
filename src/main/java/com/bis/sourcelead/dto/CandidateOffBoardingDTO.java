package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("serial")
public class CandidateOffBoardingDTO implements Serializable {
	
	private String savedoffboardId;
	
	private String offboardUser;
	
	private String firstName;
	
	private String lastName;
	
	private String mobileNumber;
	
	private String email;
	
	private String dateOfJoin;
	
	private String role;
	
	private String employmentType;
	
	private String endDate;
	
	private String reason;
	
	private String explanation;
	
	private String noticePeriod;
	
	private String workInNoticePeriod;
	
	private String effectiveDate;
	
	private String offboardingTemplate;

	
	public String getSavedoffboardId() {
		return savedoffboardId;
	}

	public String getOffboardUser() {
		return offboardUser;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public String getDateOfJoin() {
		return dateOfJoin;
	}

	public String getRole() {
		return role;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getReason() {
		return reason;
	}

	public String getExplanation() {
		return explanation;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public String getWorkInNoticePeriod() {
		return workInNoticePeriod;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public String getOffboardingTemplate() {
		return offboardingTemplate;
	}

	public void setSavedoffboardId(String savedoffboardId) {
		this.savedoffboardId = savedoffboardId;
	}

	public void setOffboardUser(String offboardUser) {
		this.offboardUser = offboardUser;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDateOfJoin(String dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public void setWorkInNoticePeriod(String workInNoticePeriod) {
		this.workInNoticePeriod = workInNoticePeriod;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setOffboardingTemplate(String offboardingTemplate) {
		this.offboardingTemplate = offboardingTemplate;
	}
	
	
}
