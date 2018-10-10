package com.bis.sourcelead.dto;

import com.bis.sourcelead.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgLocationsSessionDTO {

    private Long id;
	
	private String dateFormat;
	 
	private Integer averageWorkingHours;
	
	private String country;
	
	private String state;
	
	private String zipCode;
	
	private String city;
	
	private String location;
	
	private String latitude;
	
	private String longitude;
	
	private Status status;
	
	private String fromEmail;
	
	private String fromEmailPassword;
	
	private String phone;
	
	private String email;
	
	private String fax;
	
	private String dba;
	
	private String federalEin;
	
	private String depositSchedule;
	
	private String unemploymentTax;
	
	private String fillingForm;
	
	private String otherBusinessType;
	
	private String locationCode;
	
	private String coolingOffPeriod;
	
	private String createdBy;
	
	private String createdDate;
	
	private String updatedBy;
	
	private String updatedDate;
    
	private String modulesId;
    
	private String orgLocationName;
    
   	private String orgLocationAddress;
    
	private String website;
    
	private String orgDescription;
    
	private String isPrimary;
    
	private String generatedUserId;
    
	private String systemGeneratedId;
    
	private String loginSecurity;
    
	private String billingAddress;
    
	private String isCompanyOnboarded;
    
	private String poEmail;
	
	private String remittanceEmail;
	
	private String startMonth;
	
	private String endMonth;
	
	private Integer recommendedSkillPercent;
	
	private Integer recommendedLocationPercent;
	
	private Integer recommendedNoticePercent;
	
	private Integer recommendedExpPercent;
	
	private Integer recommendedVisatypePercent;
	
	private Integer recommendedEducationPercent;
	
	private Integer recommendedRatePercent;
	
	private Integer recommendedKeywordsPercent;
	
	private String registrationStatus;

	private String isLawFirm;
	
	private String isAccountingFirm;

	private Integer workingDaysPerWeek;
	
	private String weekOffDays;
	
    private String isTimesheetAvgHoursRequired;
	
	private Integer profilePercentage;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Integer getAverageWorkingHours() {
		return averageWorkingHours;
	}

	public void setAverageWorkingHours(Integer averageWorkingHours) {
		this.averageWorkingHours = averageWorkingHours;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getFromEmailPassword() {
		return fromEmailPassword;
	}

	public void setFromEmailPassword(String fromEmailPassword) {
		this.fromEmailPassword = fromEmailPassword;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getDba() {
		return dba;
	}

	public void setDba(String dba) {
		this.dba = dba;
	}

	public String getFederalEin() {
		return federalEin;
	}

	public void setFederalEin(String federalEin) {
		this.federalEin = federalEin;
	}

	public String getDepositSchedule() {
		return depositSchedule;
	}

	public void setDepositSchedule(String depositSchedule) {
		this.depositSchedule = depositSchedule;
	}

	public String getUnemploymentTax() {
		return unemploymentTax;
	}

	public void setUnemploymentTax(String unemploymentTax) {
		this.unemploymentTax = unemploymentTax;
	}

	public String getFillingForm() {
		return fillingForm;
	}

	public void setFillingForm(String fillingForm) {
		this.fillingForm = fillingForm;
	}

	public String getOtherBusinessType() {
		return otherBusinessType;
	}

	public void setOtherBusinessType(String otherBusinessType) {
		this.otherBusinessType = otherBusinessType;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getCoolingOffPeriod() {
		return coolingOffPeriod;
	}

	public void setCoolingOffPeriod(String coolingOffPeriod) {
		this.coolingOffPeriod = coolingOffPeriod;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getModulesId() {
		return modulesId;
	}

	public void setModulesId(String modulesId) {
		this.modulesId = modulesId;
	}

	public String getOrgLocationName() {
		return orgLocationName;
	}

	public void setOrgLocationName(String orgLocationName) {
		this.orgLocationName = orgLocationName;
	}

	public String getOrgLocationAddress() {
		return orgLocationAddress;
	}

	public void setOrgLocationAddress(String orgLocationAddress) {
		this.orgLocationAddress = orgLocationAddress;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getOrgDescription() {
		return orgDescription;
	}

	public void setOrgDescription(String orgDescription) {
		this.orgDescription = orgDescription;
	}

	public String getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(String isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getGeneratedUserId() {
		return generatedUserId;
	}

	public void setGeneratedUserId(String generatedUserId) {
		this.generatedUserId = generatedUserId;
	}

	public String getSystemGeneratedId() {
		return systemGeneratedId;
	}

	public void setSystemGeneratedId(String systemGeneratedId) {
		this.systemGeneratedId = systemGeneratedId;
	}

	public String getLoginSecurity() {
		return loginSecurity;
	}

	public void setLoginSecurity(String loginSecurity) {
		this.loginSecurity = loginSecurity;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getIsCompanyOnboarded() {
		return isCompanyOnboarded;
	}

	public void setIsCompanyOnboarded(String isCompanyOnboarded) {
		this.isCompanyOnboarded = isCompanyOnboarded;
	}

	public String getPoEmail() {
		return poEmail;
	}

	public void setPoEmail(String poEmail) {
		this.poEmail = poEmail;
	}

	public String getRemittanceEmail() {
		return remittanceEmail;
	}

	public void setRemittanceEmail(String remittanceEmail) {
		this.remittanceEmail = remittanceEmail;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public Integer getRecommendedSkillPercent() {
		return recommendedSkillPercent;
	}

	public void setRecommendedSkillPercent(Integer recommendedSkillPercent) {
		this.recommendedSkillPercent = recommendedSkillPercent;
	}

	public Integer getRecommendedLocationPercent() {
		return recommendedLocationPercent;
	}

	public void setRecommendedLocationPercent(Integer recommendedLocationPercent) {
		this.recommendedLocationPercent = recommendedLocationPercent;
	}

	public Integer getRecommendedNoticePercent() {
		return recommendedNoticePercent;
	}

	public void setRecommendedNoticePercent(Integer recommendedNoticePercent) {
		this.recommendedNoticePercent = recommendedNoticePercent;
	}

	public Integer getRecommendedExpPercent() {
		return recommendedExpPercent;
	}

	public void setRecommendedExpPercent(Integer recommendedExpPercent) {
		this.recommendedExpPercent = recommendedExpPercent;
	}

	public Integer getRecommendedVisatypePercent() {
		return recommendedVisatypePercent;
	}

	public void setRecommendedVisatypePercent(Integer recommendedVisatypePercent) {
		this.recommendedVisatypePercent = recommendedVisatypePercent;
	}

	public Integer getRecommendedEducationPercent() {
		return recommendedEducationPercent;
	}

	public void setRecommendedEducationPercent(Integer recommendedEducationPercent) {
		this.recommendedEducationPercent = recommendedEducationPercent;
	}

	public Integer getRecommendedRatePercent() {
		return recommendedRatePercent;
	}

	public void setRecommendedRatePercent(Integer recommendedRatePercent) {
		this.recommendedRatePercent = recommendedRatePercent;
	}

	public Integer getRecommendedKeywordsPercent() {
		return recommendedKeywordsPercent;
	}

	public void setRecommendedKeywordsPercent(Integer recommendedKeywordsPercent) {
		this.recommendedKeywordsPercent = recommendedKeywordsPercent;
	}

	public String getRegistrationStatus() {
		return registrationStatus;
	}

	public void setRegistrationStatus(String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	public String getIsLawFirm() {
		return isLawFirm;
	}

	public void setIsLawFirm(String isLawFirm) {
		this.isLawFirm = isLawFirm;
	}

	public String getIsAccountingFirm() {
		return isAccountingFirm;
	}

	public void setIsAccountingFirm(String isAccountingFirm) {
		this.isAccountingFirm = isAccountingFirm;
	}

	public Integer getWorkingDaysPerWeek() {
		return workingDaysPerWeek;
	}

	public void setWorkingDaysPerWeek(Integer workingDaysPerWeek) {
		this.workingDaysPerWeek = workingDaysPerWeek;
	}

	public String getWeekOffDays() {
		return weekOffDays;
	}

	public void setWeekOffDays(String weekOffDays) {
		this.weekOffDays = weekOffDays;
	}

	public String getIsTimesheetAvgHoursRequired() {
		return isTimesheetAvgHoursRequired;
	}

	public void setIsTimesheetAvgHoursRequired(String isTimesheetAvgHoursRequired) {
		this.isTimesheetAvgHoursRequired = isTimesheetAvgHoursRequired;
	}

	public Integer getProfilePercentage() {
		return profilePercentage;
	}

	public void setProfilePercentage(Integer profilePercentage) {
		this.profilePercentage = profilePercentage;
	}
}
