package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ORG_LOCATIONS")
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "orgLocations")
public class OrgLocations implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_ID")
	@JsonProperty
    private Org org;
	
	
	@ManyToOne
	@JoinColumn(name = "TIMEZONE_ID")
    private Timezone timezone;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRIMARY_CONTACT_USER_ID")
    private User user;
	
	
	@OneToMany(mappedBy="responderLocationId",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private Set<OrgRelations> orgRelationsList;
	
	@Column(name = "DATE_FORMAT")
	private String dateFormat;
	 
	@Column(name = "AVERAGE_WORKING_HOURS")
	private Integer averageWorkingHours;
	
	@Column(name = "COUNTRY", columnDefinition = "TEXT default NULL")
	private String country;
	
	@Column(name = "STATE", columnDefinition = "TEXT default NULL")
	private String state;
	
	@Column(name = "ZIP_CODE")
	private String zipCode;
	
	@Column(name = "CITY", columnDefinition = "TEXT default NULL")
	private String city;
	
	@Column(name = "LOCATION", columnDefinition = "TEXT default NULL")
	private String location;
	
	@Column(name = "LATITUDE")
	private String latitude;
	
	@Column(name = "LONGITUDE")
	private String longitude;
	
	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(name = "FROM_EMAIL")
	private String fromEmail;
	
	@Column(name = "FROM_EMAIL_PASSWORD")
	private String fromEmailPassword;
	
	@Column(name = "PHONE")
	private String phone;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "FAX")
	private String fax;
	
	@Column(name = "DBA")
	private String dba;
	
	@Column(name = "FEDERAL_EIN")
	private String federalEin;
	
	@Column(name = "DEPOSIT_SCHEDULE")
	private String depositSchedule;
	
	@Column(name = "UNEMPLOYMENT_TAX")
	private String unemploymentTax;
	
	@Column(name = "FILLING_FORM")
	private String fillingForm;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="BUSINESS_TYPE", nullable=false )	
	private LookUpData businessType;
	
	
	@Column(name = "OTHER_BUSINESS_TYPE")
	private String otherBusinessType;
	
	@Column(name = "LOCATION_CODE")
	private String locationCode;
	
	@Column(name = "COOLINGOFF_PERIOD")
	private String coolingOffPeriod;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
    @Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
    @Column(name = "UPDATED_DATE")
	private Date updatedDate;
    
    @Column(name = "MODULES_ID")
	@Type(type="text")
	private String modulesId;
    
    @Column(name = "ORG_NAME")
	private String orgLocationName;
    
    @Column(name = "ORG_LOCATION_ADDRESS")
   	@Type(type="text")
   	private String orgLocationAddress;
    
    @Column(name = "WEB_SITE")
	private String website;
    
    @Column(name = "ABOUT_COMPANY")
	@Type(type="text")
	private String orgDescription;
    
    @Column(name = "IS_PRIMARY")
	private String isPrimary;
    
    @Column(name = "GENERATED_USER_ID")
	private String generatedUserId;
    
    @Column(name = "SYSTEM_GENERATED_ID")
	private String systemGeneratedId;
    
    @Column(name = "LOGIN_SECURITY")
	private String loginSecurity;
    
    
    @Column(name = "ORG_LOCATION_BILLING_ADDRESS")
    @Type(type="text")
	private String billingAddress;
    
    
    @Column(name = "IS_COMPANY_ONBOARDED")
	private String isCompanyOnboarded;
    
    @Column(name="PO_EMAIL")
	private String poEmail;
	
	@Column(name="PAYMENT_REMITTANCE_EMIL")
	private String remittanceEmail;
	
	@Column(name="FINANCIAL_YEAR_START_MONTH")
	private String startMonth;
	
	@Column(name="FINANCIAL_YEAR_END_MONTH")
	private String endMonth;
	
	@Column(name = "RECOMMENDED_SKILL_PERCENT")
	private Integer recommendedSkillPercent;
	
	@Column(name = "RECOMMENDED_LOCATION_PERCENT")
	private Integer recommendedLocationPercent;
	
	@Column(name = "RECOMMENDED_NOTICE_PERCENT")
	private Integer recommendedNoticePercent;
	
	@Column(name = "RECOMMENDED_EXP_PERCENT")
	private Integer recommendedExpPercent;
	
	@Column(name = "RECOMMENDED_VISATYPE_PERCENT")
	private Integer recommendedVisatypePercent;
	
	@Column(name = "RECOMMENDED_EDUCATION_PERCENT")
	private Integer recommendedEducationPercent;
	
	@Column(name = "RECOMMENDED_RATE_PERCENT")
	private Integer recommendedRatePercent;
	
	@Column(name = "RECOMMENDED_KEYWORDS_PERCENT")
	private Integer recommendedKeywordsPercent;
	
	@Column(name = "REGISTRATION_STATUS")
	private String registrationStatus;

	@Column(name = "IS_LAW_FIRM")
	private String isLawFirm;
	
	@Column(name = "IS_ACCOUNTING_FIRM")
	private String isAccountingFirm;

	@Column(name = "WORKING_DAYS_PER_WEEK")
	private Integer workingDaysPerWeek;
	
	@Column(name = "WEEK_OFF_DAYS")
	private String weekOffDays;
	
	@Column(name = "IS_TIMESHEET_AVG_HOURS_REQUIRED")
    private String isTimesheetAvgHoursRequired;
	
	@Column(name = "PROFILE_PERCENTAGE")
	private Integer profilePercentage;
	
	@Column(name = "PROBATION_PERIOD")
    private String probationPeriod;
	
	@Column(name = "PROBATION_NO_OF_DAYS")
	private Integer noOfDays;
    
	@Column(name = "SHOW_COMPANY_NAME_IN_MARKETPLACE")
	private String showCompanyNameInMarkeplace;
	
    @Transient
    private String orgName;
    
    @Transient
    private String orglocationUserName;
    
    @Transient
    private String orglocationUserEmail;
    
    @Transient
    private String orglocationUserPhone;
    
    @Transient
    private String orglocationUserStatus;
    
    @Transient
    private Long orglocationUserId;
    
    @Transient
    private String orgCode;
    
    @Transient
    private List<Modules> modulesList;
    
    @Transient
    private List<Modules> subModulesList;
    
    @Transient
    private String timeZoneName;
    
    @Transient
    private String createdDateStr;
    
    @Transient
    private String orgLocationUserFirstName;
    
    @Transient
    private String orgLocationUserLastName;
    
    @Transient
    private SimpleDateFormat locFullFormDateFormatter;
    
    @Transient
    @JsonProperty
    private Long vendorOrgRelationId;
    
    @Transient
    @JsonProperty
    private Long vendorSpocId;
    
    @Transient
    @JsonProperty
    private Long vendorOnboardId;
    
    @Transient
    @JsonProperty
    private String registeredLocation;
    
    @Transient
    @JsonProperty
    private String timeZoneId;
    
    @Transient
	private Long primaryUserId;
    
    @Transient
	private String countryCode;
   
    @Transient
	private String currencyCode;
    
    @Transient
   	private Long orgId;
    
    @Transient
    private String orgImgUrl;
   
   
   
	public Timezone getTimezone() {
		return timezone;
	}

	public void setTimezone(Timezone timezone) {
		this.timezone = timezone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}


	public String getCountry() {
		return country;
	}
	
	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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

	public Set<OrgRelations> getOrgRelationsList() {
		return orgRelationsList;
	}

	public void setOrgRelationsList(Set<OrgRelations> orgRelationsList) {
		this.orgRelationsList = orgRelationsList;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getModulesId()
	{
		return modulesId;
	}

	public void setModulesId(String modulesId)
	{
		this.modulesId = modulesId;
	}

	public String getOrgLocationName()
	{
		return orgLocationName;
	}

	public void setOrgLocationName(String orgLocationName)
	{
		this.orgLocationName = orgLocationName;
	}

	public String getOrgLocationAddress()
	{
		return orgLocationAddress;
	}

	public void setOrgLocationAddress(String orgLocationAddress)
	{
		this.orgLocationAddress = orgLocationAddress;
	}

	public String getWebsite()
	{
		return website;
	}

	public void setWebsite(String website)
	{
		this.website = website;
	}

	public String getOrgDescription()
	{
		return orgDescription;
	}

	public void setOrgDescription(String orgDescription)
	{
		this.orgDescription = orgDescription;
	}

	public String getOrglocationUserName()
	{
		return orglocationUserName;
	}

	public void setOrglocationUserName(String orglocationUserName)
	{
		this.orglocationUserName = orglocationUserName;
	}

	public String getOrglocationUserEmail()
	{
		return orglocationUserEmail;
	}

	public void setOrglocationUserEmail(String orglocationUserEmail)
	{
		this.orglocationUserEmail = orglocationUserEmail;
	}

	public String getOrglocationUserPhone()
	{
		return orglocationUserPhone;
	}

	public void setOrglocationUserPhone(String orglocationUserPhone)
	{
		this.orglocationUserPhone = orglocationUserPhone;
	}

	public String getOrglocationUserStatus()
	{
		return orglocationUserStatus;
	}

	public void setOrglocationUserStatus(String orglocationUserStatus)
	{
		this.orglocationUserStatus = orglocationUserStatus;
	}

	public Long getOrglocationUserId()
	{
		return orglocationUserId;
	}

	public void setOrglocationUserId(Long orglocationUserId)
	{
		this.orglocationUserId = orglocationUserId;
	}

	public String getOrgCode()
	{
		return orgCode;
	}

	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
	}

	public List<Modules> getModulesList()
	{
		return modulesList;
	}

	public void setModulesList(List<Modules> modulesList)
	{
		this.modulesList = modulesList;
	}

	public List<Modules> getSubModulesList()
	{
		return subModulesList;
	}

	public void setSubModulesList(List<Modules> subModulesList)
	{
		this.subModulesList = subModulesList;
	}

	public String getTimeZoneName()
	{
		if(timezone != null) {
			timeZoneName =  timezone.getTimeZoneId();
		}
		
		return timeZoneName;
	}

	public void setTimeZoneName(String timeZoneName)
	{
		this.timeZoneName = timeZoneName;
	}

	public String getCreatedDateStr()
	{
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr)
	{
		this.createdDateStr = createdDateStr;
	}

	public String getIsPrimary()
	{
		return isPrimary;
	}

	public void setIsPrimary(String isPrimary)
	{
		this.isPrimary = isPrimary;
	}

	public String getOrgLocationUserFirstName()
	{
		return orgLocationUserFirstName;
	}

	public void setOrgLocationUserFirstName(String orgLocationUserFirstName)
	{
		this.orgLocationUserFirstName = orgLocationUserFirstName;
	}

	public String getOrgLocationUserLastName()
	{
		return orgLocationUserLastName;
	}

	public void setOrgLocationUserLastName(String orgLocationUserLastName)
	{
		this.orgLocationUserLastName = orgLocationUserLastName;
	}


	public String getGeneratedUserId()
	{
		return generatedUserId;
	}

	public void setGeneratedUserId(String generatedUserId)
	{
		this.generatedUserId = generatedUserId;
	}

	public String getSystemGeneratedId()
	{
		return systemGeneratedId;
	}

	public void setSystemGeneratedId(String systemGeneratedId)
	{
		this.systemGeneratedId = systemGeneratedId;
	}


	public String getDateFormat() {
		if((StringUtils.isNotEmpty(location) || StringUtils.isNotEmpty(country)) && StringUtils.isEmpty(dateFormat)){
			if((StringUtils.isNotEmpty(location) && location.contains("INDIA")) ||  StringUtils.isNotEmpty(country) && country.equalsIgnoreCase("INDIA")){
				dateFormat =  "dd-MM-yyyy";
			}else{
				dateFormat =  "MM-dd-yyyy";
			}
		}
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}


	public SimpleDateFormat getLocFullFormDateFormatter() {
		if(StringUtils.isNotEmpty(dateFormat)){
			locFullFormDateFormatter = new SimpleDateFormat(dateFormat);
		}
		if((StringUtils.isNotEmpty(location) || StringUtils.isNotEmpty(country)) && StringUtils.isEmpty(dateFormat)){
			if(( StringUtils.isNotEmpty(location) && location.contains("INDIA")) || StringUtils.isNotEmpty(country) && country.equalsIgnoreCase("INDIA")){
				   locFullFormDateFormatter = (SimpleDateFormat) Constants.DF_DMY;
			}else{
				locFullFormDateFormatter = (SimpleDateFormat) Constants.DF_MDY;
			}
		}
		return locFullFormDateFormatter;
	}


	public void setLocFullFormDateFormatter(SimpleDateFormat locFullFormDateFormatter) {
		this.locFullFormDateFormatter = locFullFormDateFormatter;
	}

	public Integer getAverageWorkingHours() {
		return averageWorkingHours;
	}

	public void setAverageWorkingHours(Integer averageWorkingHours) {
		this.averageWorkingHours = averageWorkingHours;
	}

	public Long getVendorOrgRelationId() {
		return vendorOrgRelationId;
	}

	public void setVendorOrgRelationId(Long vendorOrgRelationId) {
		this.vendorOrgRelationId = vendorOrgRelationId;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Long getVendorSpocId() {
		return vendorSpocId;
	}

	public void setVendorSpocId(Long vendorSpocId) {
		this.vendorSpocId = vendorSpocId;
	}

	public Long getVendorOnboardId() {
		return vendorOnboardId;
	}

	public void setVendorOnboardId(Long vendorOnboardId) {
		this.vendorOnboardId = vendorOnboardId;
	}


	public String getIsCompanyOnboarded() {
		return isCompanyOnboarded;
	}

	public void setIsCompanyOnboarded(String isCompanyOnboarded) {
		this.isCompanyOnboarded = isCompanyOnboarded;
	}

	public String getDba() {
		return dba;
	}

	public void setDba(String dba) {
		this.dba = dba;
	}

	public LookUpData getBusinessType() {
		return businessType;
	}

	public void setBusinessType(LookUpData businessType) {
		this.businessType = businessType;
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
	public String getLoginSecurity()
	{
		return loginSecurity;
	}

	public void setLoginSecurity(String loginSecurity)
	{
		this.loginSecurity = loginSecurity;

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

	public String getStartMonth()
	{
		return startMonth;
	}

	public void setStartMonth(String startMonth)
	{
		this.startMonth = startMonth;
	}

	public String getEndMonth()
	{
		return endMonth;
	}

	public void setEndMonth(String endMonth)
	{
		this.endMonth = endMonth;
	}

	public Integer getRecommendedSkillPercent()
	{
		return recommendedSkillPercent;
	}

	public void setRecommendedSkillPercent(Integer recommendedSkillPercent)
	{
		this.recommendedSkillPercent = recommendedSkillPercent;
	}

	public Integer getRecommendedLocationPercent()
	{
		return recommendedLocationPercent;
	}

	public void setRecommendedLocationPercent(Integer recommendedLocationPercent)
	{
		this.recommendedLocationPercent = recommendedLocationPercent;
	}

	public Integer getRecommendedNoticePercent()
	{
		return recommendedNoticePercent;
	}

	public void setRecommendedNoticePercent(Integer recommendedNoticePercent)
	{
		this.recommendedNoticePercent = recommendedNoticePercent;
	}

	public Integer getRecommendedExpPercent()
	{
		return recommendedExpPercent;
	}

	public void setRecommendedExpPercent(Integer recommendedExpPercent)
	{
		this.recommendedExpPercent = recommendedExpPercent;
	}

	public Integer getRecommendedVisatypePercent()
	{
		return recommendedVisatypePercent;
	}

	public void setRecommendedVisatypePercent(Integer recommendedVisatypePercent)
	{
		this.recommendedVisatypePercent = recommendedVisatypePercent;
	}

	public Integer getRecommendedEducationPercent()
	{
		return recommendedEducationPercent;
	}

	public void setRecommendedEducationPercent(Integer recommendedEducationPercent)
	{
		this.recommendedEducationPercent = recommendedEducationPercent;
	}

	public Integer getRecommendedRatePercent()
	{
		return recommendedRatePercent;
	}

	public void setRecommendedRatePercent(Integer recommendedRatePercent)
	{
		this.recommendedRatePercent = recommendedRatePercent;
	}

	public Integer getRecommendedKeywordsPercent()
	{
		return recommendedKeywordsPercent;
	}

	public void setRecommendedKeywordsPercent(Integer recommendedKeywordsPercent)
	{
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

	public String getRegisteredLocation() {
		
		if(StringUtils.isNotEmpty(registeredLocation))
			return registeredLocation;
		else{
			if(StringUtils.isNotEmpty(country) && StringUtils.isEmpty(state) && StringUtils.isEmpty(city)){
				registeredLocation = orgLocationName+"-"+country;
			}else if(StringUtils.isNotEmpty(country) && StringUtils.isNotEmpty(state) && StringUtils.isEmpty(city)){
				registeredLocation = orgLocationName+"-"+country +" "+state;
			}else if(StringUtils.isNotEmpty(country) && StringUtils.isEmpty(state) && StringUtils.isNotEmpty(city)){
				registeredLocation = orgLocationName+"-"+country +" "+city;
			}else if(StringUtils.isNotEmpty(country) && StringUtils.isNotEmpty(state) && StringUtils.isNotEmpty(city)){
				registeredLocation = orgLocationName+"-"+country+" "+state+" "+city;
			}
			return registeredLocation;
		}
		
	}

	public void setRegisteredLocation(String registeredLocation) {
		this.registeredLocation = registeredLocation;
	}

	public Integer getProfilePercentage() {
		return profilePercentage;
	}

	public void setProfilePercentage(Integer profilePercentage) {
		this.profilePercentage = profilePercentage;
	}

	public String getProbationPeriod() {
		return probationPeriod;
	}

	public void setProbationPeriod(String probationPeriod) {
		this.probationPeriod = probationPeriod;
	}

	public Integer getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getShowCompanyNameInMarkeplace() {
		return showCompanyNameInMarkeplace;
	}

	public void setShowCompanyNameInMarkeplace(String showCompanyNameInMarkeplace) {
		this.showCompanyNameInMarkeplace = showCompanyNameInMarkeplace;
	}

	public String getTimeZoneId() {
		return timeZoneId;
	}

	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	public Long getPrimaryUserId() {
		return primaryUserId;
	}

	public void setPrimaryUserId(Long primaryUserId) {
		this.primaryUserId = primaryUserId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgImgUrl() {
		return orgImgUrl;
	}

	public void setOrgImgUrl(String orgImgUrl) {
		this.orgImgUrl = orgImgUrl;
	}
	
}
