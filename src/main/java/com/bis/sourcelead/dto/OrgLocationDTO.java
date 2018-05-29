package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class OrgLocationDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String orgName;
	
	private String locationCode;
	
	private String location;
	
	private String zipCode;
	
	private String orgCode;
	
	private long orgId;
	
	private long orgLocationId;
	
	private long candidateId;
	
	private long orgLinceseSubscriptionId;
	
	private String orgImageUrl;
	
	private String country;
	
	private String state;
	
	private String city;
	
	private String orgLocationAddress;
	
	private String website;
	
	private String isPrimaryOrg;
	
	private String userType;
	
	List<String> menuList;
	
	//List<String> roleActionsList;
	
	List<String> menuIconsList;
	
	private long vendorOrgLocationId;
	
	private long vendorOrgRelationId;
	
	private long vendorOnboardId;
	
	private int recommSkill;
	
	private int recommLocation;
	
	private int recommNoticePeriod;
	
	private int recommExp;
	
	private int recommVisaType;
	
	private int recommEducation;
	
	private int recommRate;
	
	private int recommKeywords;
	
	private String registrationStatusStr;
	
	private String email;
	
	private String firstName;
	
	private String lastName;
	
	private String phoneNumber;
	
	private String dateFormat;
	
	private Long timeZone;
	
	private Long subscriptionType;
	
    private Long userSubscriptionType;
  
    private Long storageSubscriptionType;
    
    private String subscriptionDate;
    
    private Double subscriptionRate;
	

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public String getLocationCode()
	{
		return locationCode;
	}

	public void setLocationCode(String locationCode)
	{
		this.locationCode = locationCode;
	}

	public String getOrgCode()
	{
		return orgCode;
	}

	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
	}

	public long getOrgId()
	{
		return orgId;
	}

	public void setOrgId(long orgId)
	{
		this.orgId = orgId;
	}

	public long getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(long orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public long getCandidateId()
	{
		return candidateId;
	}

	public void setCandidateId(long candidateId)
	{
		this.candidateId = candidateId;
	}

	public String getOrgImageUrl()
	{
		return orgImageUrl;
	}

	public void setOrgImageUrl(String orgImageUrl)
	{
		this.orgImageUrl = orgImageUrl;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
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

	public String getIsPrimaryOrg()
	{
		return isPrimaryOrg;
	}

	public void setIsPrimaryOrg(String isPrimaryOrg)
	{
		this.isPrimaryOrg = isPrimaryOrg;
	}

	public String getUserType()
	{
		return userType;
	}

	public void setUserType(String userType)
	{
		this.userType = userType;
	}

	public List<String> getMenuList()
	{
		return menuList;
	}

	public void setMenuList(List<String> menuList)
	{
		this.menuList = menuList;
	}

	public List<String> getMenuIconsList()
	{
		return menuIconsList;
	}

	public void setMenuIconsList(List<String> menuIconsList)
	{
		this.menuIconsList = menuIconsList;
	}

	public long getVendorOrgLocationId()
	{
		return vendorOrgLocationId;
	}

	public void setVendorOrgLocationId(long vendorOrgLocationId)
	{
		this.vendorOrgLocationId = vendorOrgLocationId;
	}

	public long getVendorOrgRelationId()
	{
		return vendorOrgRelationId;
	}

	public void setVendorOrgRelationId(long vendorOrgRelationId)
	{
		this.vendorOrgRelationId = vendorOrgRelationId;
	}

	public long getVendorOnboardId()
	{
		return vendorOnboardId;
	}

	public void setVendorOnboardId(long vendorOnboardId)
	{
		this.vendorOnboardId = vendorOnboardId;
	}

	public int getRecommSkill()
	{
		return recommSkill;
	}

	public void setRecommSkill(int recommSkill)
	{
		this.recommSkill = recommSkill;
	}

	public int getRecommLocation()
	{
		return recommLocation;
	}

	public void setRecommLocation(int recommLocation)
	{
		this.recommLocation = recommLocation;
	}

	public int getRecommNoticePeriod()
	{
		return recommNoticePeriod;
	}

	public void setRecommNoticePeriod(int recommNoticePeriod)
	{
		this.recommNoticePeriod = recommNoticePeriod;
	}

	public int getRecommExp()
	{
		return recommExp;
	}

	public void setRecommExp(int recommExp)
	{
		this.recommExp = recommExp;
	}

	public int getRecommVisaType()
	{
		return recommVisaType;
	}

	public void setRecommVisaType(int recommVisaType)
	{
		this.recommVisaType = recommVisaType;
	}

	public int getRecommEducation()
	{
		return recommEducation;
	}

	public void setRecommEducation(int recommEducation)
	{
		this.recommEducation = recommEducation;
	}

	public int getRecommRate()
	{
		return recommRate;
	}

	public void setRecommRate(int recommRate)
	{
		this.recommRate = recommRate;
	}

	public int getRecommKeywords()
	{
		return recommKeywords;
	}

	public void setRecommKeywords(int recommKeywords)
	{
		this.recommKeywords = recommKeywords;
	}

	public String getRegistrationStatusStr() {
		return registrationStatusStr;
	}

	public void setRegistrationStatusStr(String registrationStatusStr) {
		this.registrationStatusStr = registrationStatusStr;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Long getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(Long timeZone) {
		this.timeZone = timeZone;
	}

	public long getOrgLinceseSubscriptionId() {
		return orgLinceseSubscriptionId;
	}

	public void setOrgLinceseSubscriptionId(long orgLinceseSubscriptionId) {
		this.orgLinceseSubscriptionId = orgLinceseSubscriptionId;
	}

	public Long getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(Long subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public Long getUserSubscriptionType() {
		return userSubscriptionType;
	}

	public void setUserSubscriptionType(Long userSubscriptionType) {
		this.userSubscriptionType = userSubscriptionType;
	}

	public Long getStorageSubscriptionType() {
		return storageSubscriptionType;
	}

	public void setStorageSubscriptionType(Long storageSubscriptionType) {
		this.storageSubscriptionType = storageSubscriptionType;
	}

	public String getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(String subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	public Double getSubscriptionRate() {
		return subscriptionRate;
	}

	public void setSubscriptionRate(Double subscriptionRate) {
		this.subscriptionRate = subscriptionRate;
	}

	

	
}
