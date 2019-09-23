package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.annotations.Email;
import com.bis.sourcelead.annotations.Required;

public class OrgSettingsDTO extends CommonDTO{

	private Integer id;
	
	private String firstName;
	
	private String lastName;
	
	@Required(desc = "company name")
	private String companyName;
	
	@Required(desc = "website")
	private String website;
	
	private String email;
	
	@Required(desc = "country")
	private String country;
	
	@Required(desc = "state")
	private String state;
	
	@Required(desc = "city")
	private String city;
	
	private String address;
	
	private String password;
	
	private String verificationCode;
	
	@Required(desc = "timeZone")
	private String timeZone;
	
	private String mobilePhone;
	
	private String registeredOrgId;
	
	private String orgLinceseSubscriptionId;
	
	private String orgLocationId;
	
	private String location;
	
	private String zipCode;
	
	private String latitude;
	
	private String longtude;
	
	private String dateFormat;
	
	private String dateFormat1;
	
	private String dba;
	
	private String workingDaysPerWeek;
	
	private String weekOffDays;
	
	@Required(desc = "generatedUserId")
	private String generatedUserId;
	
	private Integer averageWorkingHours;
	
	private String staticPath;
	
	private String poEmail;
    
    private String remittanceEmail;
    
    @Required(desc = "businessType")
    private String businessType;
    
    private String otherBusinessType;
    
    private String systemGeneratedId;
    
    private String phone;
	
	private String fax;
	
	private String aboutCompany;

	private List<FileInfo> fileInfo;
	
	private String subscriptionDate;
	
	private String isTimesheetAvgHoursRequired;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getRegisteredOrgId() {
		return registeredOrgId;
	}

	public void setRegisteredOrgId(String registeredOrgId) {
		this.registeredOrgId = registeredOrgId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongtude() {
		return longtude;
	}

	public void setLongtude(String longtude) {
		this.longtude = longtude;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getDba() {
		return dba;
	}

	public void setDba(String dba) {
		this.dba = dba;
	}

	public String getGeneratedUserId() {
		return generatedUserId;
	}

	public void setGeneratedUserId(String generatedUserId) {
		this.generatedUserId = generatedUserId;
	}

	public Integer getAverageWorkingHours() {
		return averageWorkingHours;
	}

	public void setAverageWorkingHours(Integer averageWorkingHours) {
		this.averageWorkingHours = averageWorkingHours;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
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

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getOtherBusinessType() {
		return otherBusinessType;
	}

	public void setOtherBusinessType(String otherBusinessType) {
		this.otherBusinessType = otherBusinessType;
	}

	public String getSystemGeneratedId() {
		return systemGeneratedId;
	}

	public void setSystemGeneratedId(String systemGeneratedId) {
		this.systemGeneratedId = systemGeneratedId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAboutCompany() {
		return aboutCompany;
	}

	public void setAboutCompany(String aboutCompany) {
		this.aboutCompany = aboutCompany;
	}

	public String getDateFormat1()
	{
		return dateFormat1;
	}

	public void setDateFormat1(String dateFormat1)
	{
		this.dateFormat1 = dateFormat1;
	}

	public String getOrgLinceseSubscriptionId() {
		return orgLinceseSubscriptionId;
	}

	public void setOrgLinceseSubscriptionId(String orgLinceseSubscriptionId) {
		this.orgLinceseSubscriptionId = orgLinceseSubscriptionId;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(String subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	public String getWorkingDaysPerWeek() {
		return workingDaysPerWeek;
	}

	public void setWorkingDaysPerWeek(String workingDaysPerWeek) {
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

	
}
