package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class CandidateMarketPlaceDTO extends SearchDTO implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Long id;

	private String candidateId;
	
	private String masterCandidateId;

	private String orgLocationId;

	private String fullName;

	private String email;

	private String alternateEmail;

	private String alternateMobilePhone;

	private String mobilePhone;

	private String location;
	
	private Integer profilePercentage;
	
	private String experienceYears;

	private String experienceMonths;

	private String noticePeriod;

	private String designation;

	private String employmentType;

	private String currentRate;

	private String currentRateType;

	private String currentRateCurrency;

	private String expectedRate;

	private String expectedRateType;

	private String expectedRateCurrencey;
	
	private String marketPrice;

	private String marketRateCurrencey;
	
	private String status;

	private String createdDate;

	private String createdBy;

	private String updatedDate;

	private String updatedBy;

	private String candidateUUID;

	private String skillsName;
	
	private String gender;

	private String nationality;

	private String maritalStatus;
	
	private String imageUrl;
	
	private String marketplaceUUID;
	
	private String candidateMarketStatus;
	
	private String showCompanyName;
	
	private String companyName;
	
	private String summary;
	
	private String marketCartStatus;
	
	private String[] skills;
	
	private String isFavourite;
	
	private String isAssociate;
	
	private List<CandidateEmployerDTO> candidateEmployers;
	
	private List<CandidateEducationDTO> candidateEducation;
	
	private List<CandidateCertificationDTO> candidateCertifications;
	
	private List<CandidateMarketPlaceDTO> candidateMarketPlaceDTOs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMasterCandidateId() {
		return masterCandidateId;
	}

	public void setMasterCandidateId(String masterCandidateId) {
		this.masterCandidateId = masterCandidateId;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlternateEmail() {
		return alternateEmail;
	}

	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}

	public String getAlternateMobilePhone() {
		return alternateMobilePhone;
	}

	public void setAlternateMobilePhone(String alternateMobilePhone) {
		this.alternateMobilePhone = alternateMobilePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getProfilePercentage() {
		return profilePercentage;
	}

	public void setProfilePercentage(Integer profilePercentage) {
		this.profilePercentage = profilePercentage;
	}

	public String getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(String experienceYears) {
		this.experienceYears = experienceYears;
	}

	public String getExperienceMonths() {
		return experienceMonths;
	}

	public void setExperienceMonths(String experienceMonths) {
		this.experienceMonths = experienceMonths;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getCurrentRate() {
		return currentRate;
	}

	public void setCurrentRate(String currentRate) {
		this.currentRate = currentRate;
	}

	public String getCurrentRateType() {
		return currentRateType;
	}

	public void setCurrentRateType(String currentRateType) {
		this.currentRateType = currentRateType;
	}

	public String getCurrentRateCurrency() {
		return currentRateCurrency;
	}

	public void setCurrentRateCurrency(String currentRateCurrency) {
		this.currentRateCurrency = currentRateCurrency;
	}

	public String getExpectedRate() {
		return expectedRate;
	}

	public void setExpectedRate(String expectedRate) {
		this.expectedRate = expectedRate;
	}

	public String getExpectedRateType() {
		return expectedRateType;
	}

	public void setExpectedRateType(String expectedRateType) {
		this.expectedRateType = expectedRateType;
	}

	public String getExpectedRateCurrencey() {
		return expectedRateCurrencey;
	}

	public void setExpectedRateCurrencey(String expectedRateCurrencey) {
		this.expectedRateCurrencey = expectedRateCurrencey;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getMarketRateCurrencey() {
		return marketRateCurrencey;
	}

	public void setMarketRateCurrencey(String marketRateCurrencey) {
		this.marketRateCurrencey = marketRateCurrencey;
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

	public String getCandidateUUID() {
		return candidateUUID;
	}

	public void setCandidateUUID(String candidateUUID) {
		this.candidateUUID = candidateUUID;
	}

	public String getSkillsName() {
		return skillsName;
	}

	public void setSkillsName(String skillsName) {
		this.skillsName = skillsName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getMarketplaceUUID() {
		return marketplaceUUID;
	}

	public void setMarketplaceUUID(String marketplaceUUID) {
		this.marketplaceUUID = marketplaceUUID;
	}

	public String getCandidateMarketStatus() {
		return candidateMarketStatus;
	}

	public void setCandidateMarketStatus(String candidateMarketStatus) {
		this.candidateMarketStatus = candidateMarketStatus;
	}

	public String getShowCompanyName() {
		return showCompanyName;
	}

	public void setShowCompanyName(String showCompanyName) {
		this.showCompanyName = showCompanyName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getMarketCartStatus() {
		return marketCartStatus;
	}

	public void setMarketCartStatus(String marketCartStatus) {
		this.marketCartStatus = marketCartStatus;
	}

	public String[] getSkills() {
		return skills;
	}

	public void setSkills(String[] skills) {
		this.skills = skills;
	}

	public String getIsFavourite() {
		return isFavourite;
	}

	public void setIsFavourite(String isFavourite) {
		this.isFavourite = isFavourite;
	}

	public String getIsAssociate() {
		return isAssociate;
	}

	public void setIsAssociate(String isAssociate) {
		this.isAssociate = isAssociate;
	}
	
	public List<CandidateEmployerDTO> getCandidateEmployers() {
		return candidateEmployers;
	}

	public void setCandidateEmployers(List<CandidateEmployerDTO> candidateEmployers) {
		this.candidateEmployers = candidateEmployers;
	}

	public List<CandidateEducationDTO> getCandidateEducation() {
		return candidateEducation;
	}

	public void setCandidateEducation(List<CandidateEducationDTO> candidateEducation) {
		this.candidateEducation = candidateEducation;
	}

	public List<CandidateCertificationDTO> getCandidateCertifications() {
		return candidateCertifications;
	}

	public void setCandidateCertifications(List<CandidateCertificationDTO> candidateCertifications) {
		this.candidateCertifications = candidateCertifications;
	}

	public List<CandidateMarketPlaceDTO> getCandidateMarketPlaceDTOs() {
		return candidateMarketPlaceDTOs;
	}

	public void setCandidateMarketPlaceDTOs(List<CandidateMarketPlaceDTO> candidateMarketPlaceDTOs) {
		this.candidateMarketPlaceDTOs = candidateMarketPlaceDTOs;
	}
	
}
