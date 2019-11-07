package com.bis.sourcelead.form;

import javax.persistence.Column;

import org.springframework.web.multipart.MultipartFile;

public class OrgSettings {
	
	private Integer Id;
	
	private MultipartFile file;
	
	private String companyName;
	
	private String website;
	
	private String aboutCompany;
	
	private String country;
	
	private String state;
	
	private String city;
	
	private String zipcode;
	
	private String phone;
	
	private String fax;
	
	private String address;
	
	private String timeZone;
	
	private String dateFormat1;

    private String generatedUserId;

    private String systemGeneratedId;
    
    private String dba;
    
    private String businessType;
    
    private String otherBusinessType;
    
    private Integer averageWorkingHours;
    
    private String poEmail;
    
    private String remittanceEmail;
    
    private String workingDaysPerWeek;
    
    private String weekOffDays;
    
    private String isTimesheetAvgHoursRequired;
	

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
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

	public String getAboutCompany() {
		return aboutCompany;
	}

	public void setAboutCompany(String aboutCompany) {
		this.aboutCompany = aboutCompany;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}


	public String getDateFormat1() {
		return dateFormat1;
	}

	public void setDateFormat1(String dateFormat1) {
		this.dateFormat1 = dateFormat1;
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

	public String getDba() {
		return dba;
	}

	public void setDba(String dba) {
		this.dba = dba;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Integer getAverageWorkingHours() {
		return averageWorkingHours;
	}

	public void setAverageWorkingHours(Integer averageWorkingHours) {
		this.averageWorkingHours = averageWorkingHours;
	}

	public String getOtherBusinessType() {
		return otherBusinessType;
	}

	public void setOtherBusinessType(String otherBusinessType) {
		this.otherBusinessType = otherBusinessType;
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
