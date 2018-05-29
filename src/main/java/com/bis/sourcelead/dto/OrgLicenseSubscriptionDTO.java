package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class OrgLicenseSubscriptionDTO implements Serializable{

    private static final long serialVersionUID = 1L;
	
	private long orgLinceseSubscriptionId;
	
	@Required(desc = "registeredModules")
	private String registeredModules;
	
	private Double invoiceAmount;
	
	private Long orgLocationId;
	
	private String currency;
	
	@Required(desc = "country")
	private String country;
	
	private String fromDateStr;
	
	private String toDateStr;
	
	private List<String> modulesList;
	
	private List<Long> modulesIdsList;
	
	@Required(desc = "subscriptionType")
	private String subscriptionType;
	
	@Required(desc = "storageType")
	private String storageType;
	
	@Required(desc = "userSubscription")
	private String userSubscription;
	
	@Required(desc = "subscriptionDate")
	private String subscriptionDate;
	
	@Required(desc = "timezone")
	private String timezone;
	
	private String licenseUUID;

	public long getOrgLinceseSubscriptionId() {
		return orgLinceseSubscriptionId;
	}

	public void setOrgLinceseSubscriptionId(long orgLinceseSubscriptionId) {
		this.orgLinceseSubscriptionId = orgLinceseSubscriptionId;
	}

	public String getRegisteredModules() {
		return registeredModules;
	}

	public void setRegisteredModules(String registeredModules) {
		this.registeredModules = registeredModules;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFromDateStr() {
		return fromDateStr;
	}

	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}

	public String getToDateStr() {
		return toDateStr;
	}

	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}

	public List<String> getModulesList() {
		return modulesList;
	}

	public void setModulesList(List<String> modulesList) {
		this.modulesList = modulesList;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getUserSubscription() {
		return userSubscription;
	}

	public void setUserSubscription(String userSubscription) {
		this.userSubscription = userSubscription;
	}

	public String getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(String subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getLicenseUUID() {
		return licenseUUID;
	}

	public void setLicenseUUID(String licenseUUID) {
		this.licenseUUID = licenseUUID;
	}

	public List<Long> getModulesIdsList() {
		return modulesIdsList;
	}

	public void setModulesIdsList(List<Long> modulesIdsList) {
		this.modulesIdsList = modulesIdsList;
	}
}
